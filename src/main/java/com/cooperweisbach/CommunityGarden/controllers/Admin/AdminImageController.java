package com.cooperweisbach.CommunityGarden.controllers.Admin;

import com.cooperweisbach.CommunityGarden.exceptions.FileStorageException;
import com.cooperweisbach.CommunityGarden.models.FileUploadUtil;
import com.cooperweisbach.CommunityGarden.models.Image;
import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class AdminImageController {

    private MemberServices memberServices;
    private LeasableServices leasableServices;
    private MemberStatusServices memberStatusServices;
    private UserRolesServices userRolesServices;
    private LeaseServices leaseServices;
    private PostServices postServices;
    private ImageServices imageServices;
    private PaymentServices paymentServices;
    private MessageThreadServices messageThreadServices;
    private ImageTypeServices imageTypeServices;

    @Autowired
    public AdminImageController(MemberServices memberServices,
                                  LeasableServices leasableServices,
                                  MemberStatusServices memberStatusServices,
                                  UserRolesServices userRolesServices,
                                  LeaseServices leaseServices,
                                  PostServices postServices,
                                  ImageServices imageServices,
                                  PaymentServices paymentServices,
                                  MessageThreadServices messageThreadServices,
                                  ImageTypeServices imageTypeServices) {
        this.memberServices = memberServices;
        this.leasableServices = leasableServices;
        this.memberStatusServices = memberStatusServices;
        this.userRolesServices = userRolesServices;
        this.leaseServices = leaseServices;
        this.postServices = postServices;
        this.imageServices = imageServices;
        this.paymentServices = paymentServices;
        this.messageThreadServices = messageThreadServices;
        this.imageTypeServices = imageTypeServices;
    }

    @GetMapping("/admin/images")
    public String adminGetAllImages(Model m, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            m.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        m.addAttribute("allImages", imageServices.getAllImages());

        m.addAttribute("imageToUpload", new Image());
        m.addAttribute("imageToAlter", new Image());
        m.addAttribute("imageTypes", imageTypeServices.getAllImageTypes());
        return "admin/images/images";
    }


    @PostMapping("/admin/images")
    public String adminGetAllImagesPost(Model m, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            m.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        m.addAttribute("allImages", imageServices.getAllImages());

        m.addAttribute("imageToUpload", new Image());
        m.addAttribute("imageToAlter", new Image());
        m.addAttribute("imageTypes", imageTypeServices.getAllImageTypes());
        return "admin/images/images";
    }



    @PostMapping("/admin/images/upload")
    public String adminUploadNewImages(Model m){
        m.addAttribute("imageToUpload", new Image());
        m.addAttribute("imageTypes", imageTypeServices.getAllImageTypes());
        return "/admin/images/image-upload";
    }

   @Value("${app.upload.dir}")
    public String uploadDir;

    //https://www.codejava.net/frameworks/spring-boot/spring-boot-file-upload-tutorial
    @PostMapping("/admin/images/upload-approved")
    public ModelAndView redirectToSuccessfulUpload(HttpServletRequest request,
                                                   @ModelAttribute("imageToUpload") Image imageToUpload,
                                                   @RequestParam("image") MultipartFile multipartFile) throws IOException, FileStorageException {
        log.warn("blah blah blah bl");
//        log.warn(request.getUserPrincipal().getName());
//        int id = memberServices.getMemberByEmail(request.getUserPrincipal().getName()).getMemberId();
//        FileUploadUtil.saveFile(imageToUpload.getImageType(), multipartFile, false, id);
        try {
            //file name clean up
            List<String> locationsToSave = imageToUpload.getImageType().stream().map(imageType -> imageType.getImageType()).collect(Collectors.toList());
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            //location of the file on the disk
            for(String location: locationsToSave){
                Path current = Paths.get(uploadDir + File.separator + location);
                if (!Files.exists(current)) {
                    Files.createDirectories(current);
                }
                Path copyLocation = Paths.get(current + File.separator + fileName);
                  //Saving file to location and setting what happens in case of duplicate file name
                Files.copy(multipartFile.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            }
            imageToUpload.setImageUploadName(fileName);
            imageServices.save(imageToUpload);
        } catch(Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + multipartFile.getOriginalFilename() + ". Please try again.");
        }
        log.warn("Saved image " + imageToUpload.getImageId() + " with image name " + multipartFile.getOriginalFilename());
//        String uploadDir = "/images/";
//        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);



//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        imageToUpload.setImageUploadName(fileName);
//        imageServices.save(imageToUpload);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/images");
    }

    @PostMapping("/admin/images/delete/{id}")
    public String deletePhotoById(@PathVariable("id") Integer id, Model model){
        Image imageToDelete = imageServices.getImageById(id);
        model.addAttribute("imageToDelete", imageToDelete);
        return "/admin/images/image-delete";
    }
    @PostMapping("/admin/images/delete-approved")
    public ModelAndView redirectSuccesfulPhotoDeletion(HttpServletRequest request, @ModelAttribute("imageToAlter") Image imageToAlter) {
        imageToAlter = imageServices.getImageById(imageToAlter.getImageId());
        String fileName = imageToAlter.getImageUploadName();
        Path deleteLocation = Paths.get(uploadDir + File.separator + fileName);

        try {
            Files.delete(deleteLocation);
            imageServices.deleteById(imageToAlter.getImageId());
        } catch(Exception e){
            e.printStackTrace();
        }
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/images");
    }

    @PostMapping("/admin/images/update/{id}")
    public String updateImageById(@PathVariable("id") Integer id, Model model){
        Image imageToUpdate = imageServices.getImageById(id);
        model.addAttribute("imageToUpdate", imageToUpdate);
        model.addAttribute("imageTypes", imageTypeServices.getAllImageTypes());
        return "/admin/images/image-update";
    }

    @PostMapping("/admin/images/update-approved")
    public ModelAndView updateImageById(HttpServletRequest request, @ModelAttribute("imageToAlter") Image imageToAlter){
        Image databaseImage = imageServices.getImageById(imageToAlter.getImageId());
        databaseImage.setImageDescription(imageToAlter.getImageDescription());
        databaseImage.setImageType(imageToAlter.getImageType());
        imageServices.save(databaseImage);
        return new ModelAndView("redirect:/admin/images");
    }

}

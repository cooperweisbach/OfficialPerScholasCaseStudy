package com.cooperweisbach.CommunityGarden.controllers.Member;

import com.cooperweisbach.CommunityGarden.exceptions.UserAlreadyExistsException;
import com.cooperweisbach.CommunityGarden.models.FileUploadUtil;
import com.cooperweisbach.CommunityGarden.models.Image;
import com.cooperweisbach.CommunityGarden.models.ImageType;
import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.security.MemberDTO;
import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;

@Controller
@Slf4j
public class MemberAccountController {

    private MemberServices memberServices;
    private LeasableServices leasableServices;
    private LeaseServices leaseServices;
    private PostServices postServices;
    private MessageThreadServices messageThreadServices;
    private UserRolesServices userRolesServices;
    private ImageServices imageServices;
    private ConfigurationServices configurationServices;
    private ImageTypeServices imageTypeServices;


    @Autowired
    public MemberAccountController(MemberServices memberServices,
                           LeasableServices leasableServices,
                           LeaseServices leaseServices,
                           PostServices postServices,
                           MessageThreadServices messageThreadServices,
                           UserRolesServices userRolesServices,
                           ImageServices imageServices,
                           ConfigurationServices configurationServices,
                                   ImageTypeServices imageTypeServices) {
        this.memberServices = memberServices;
        this.leasableServices = leasableServices;
        this.leaseServices = leaseServices;
        this.postServices = postServices;
        this.messageThreadServices = messageThreadServices;
        this.userRolesServices = userRolesServices;
        this.imageServices = imageServices;
        this.configurationServices = configurationServices;
        this.imageTypeServices = imageTypeServices;
    }


    @GetMapping("/members/leasables")
    public String leasables(Model m, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            m.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        log.warn("entered mapping config");
        m.addAttribute("currentConfig", configurationServices.getPublishedConfiguration());
        return "/member/leasables";
    }

    @PostMapping("/members/leasables/rent")
    public String rentLeasables(Model m, @RequestParam("id") Integer id, HttpServletRequest request, Model model){
        log.warn("Entered the rent page");
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            model.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        model.addAttribute("leasableToRent",leasableServices.getLeasableById(id));
        log.warn("The id value returned is: "+ id);
        return "member/rent";
    }

    @GetMapping("/members")
    public String memberHomePage(Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            model.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        model.addAttribute("messageThreads", messageThreadServices.getAllMessageThreads());
        return "member/message-threads";
    }

    @GetMapping("/members/member")
    public String memberInfoPage(Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            model.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        model.addAttribute("memberToUpdate", new Member());
        return "member/member";
    }

    @PostMapping("/members/member/update-info")
    public ModelAndView updateInfo(Model model, HttpServletRequest request, @RequestParam("memberToUpdate") Member memberToUpdate){

        Member user = memberServices.getMemberByEmail(request.getUserPrincipal().getName());
        user.setFirstName(memberToUpdate.getFirstName());
        user.setLastName(memberToUpdate.getLastName());
        user.setEmail(memberToUpdate.getEmail());
        user.setPhoneNumber(memberToUpdate.getPhoneNumber());
        memberServices.save(user);

        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/members/member");
    }

    @PostMapping("/members/member/update-profile-pic")
    public ModelAndView updateProfilePic(HttpServletRequest request, @RequestParam("profilePic") MultipartFile profilePic) throws IOException {
        Member member = memberServices.getMemberByEmail(request.getUserPrincipal().getName());
        int id =  member.getMemberId();
        log.warn("start of saving image");
        //Delete the temporarily held profile picture that was simply used to display to the user how the upload would look.
        ImageType temporaryType = imageTypeServices.getByImageType("temporary");
        ArrayList<ImageType> listTemporary = new ArrayList<>();
        listTemporary.add(temporaryType);
        FileUploadUtil.deleteFile(listTemporary, profilePic, true, id);
        log.warn("delete temporary file");
        //Delete the previous profile picture folder if it exists
        FileUploadUtil.deleteProfilePicFolder(id);
        log.warn("delete profile pic folder");
        //Create a new folder with the new image
        ImageType profilePicType = imageTypeServices.getByImageType("profilePicture");
        ArrayList<ImageType> listProfilePic = new ArrayList<>();
        listProfilePic.add(profilePicType);
        FileUploadUtil.saveFile(listProfilePic ,profilePic, true, id);
        log.warn("save new profile pic");
        //Add the images to the database and set it as the member's new profile pic.
        FileUploadUtil.setNewProfilePic(profilePic, member, imageTypeServices, imageServices, memberServices);
        log.warn("create a new image instance");

        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/members/member");
    }

    @PostMapping("/members/member/update-password")
    public ModelAndView updatePassword(HttpServletRequest request){

        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/members/member");
    }

    @PostMapping("/members/member/terminate-lease")
    public ModelAndView terminateLease(HttpServletRequest request){

        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/members/member");
    }

    @PostMapping("/members/leasables/rent-confirmed")
    public ModelAndView rentalConfirmed(HttpServletRequest request, @RequestParam("leasableRented")Integer leasableRented ){
        Principal principal = request.getUserPrincipal();
        Member member = memberServices.getMemberByEmail(principal.getName());
        leaseServices.createNewLease(leasableRented, member);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/members/leases");
    }

//    @GetMapping("/members/leases")
//    public String viewLeases(HttpServletRequest request, Model model){
//        Principal principal = request.getUserPrincipal();
//        Member member = memberServices.getMemberByEmail(principal.getName());
//        model.addAttribute("memberLeases", member.getLeases());
//        return "member/leases";
//    }

    @GetMapping("/registration")
    public String register(Model model){
        MemberDTO newMember = new MemberDTO();
        model.addAttribute("newMember", newMember);
        return "registration";
    }

    @PostMapping("/registration/processing")
    public ModelAndView processRegistration(@ModelAttribute("newMember") @Valid MemberDTO memberDTO,
                                            HttpServletRequest request, Errors errors){
        log.warn("registration processing");
        log.warn(memberDTO.getFirstName());
        log.warn(memberDTO.getEmail());

        ModelAndView mav = new ModelAndView();
        try{
            Member newMember = memberServices.registerNewMember(memberDTO);
        } catch(UserAlreadyExistsException uaee){
            log.warn("User already exists");
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        }
        mav.setViewName("redirect:/");
//        mav.addObject("newMember", memberDTO);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return mav;
    }


}

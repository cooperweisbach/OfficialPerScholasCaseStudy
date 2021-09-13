package com.cooperweisbach.CommunityGarden.models;

import com.cooperweisbach.CommunityGarden.services.ImageServices;
import com.cooperweisbach.CommunityGarden.services.ImageTypeServices;
import com.cooperweisbach.CommunityGarden.services.MemberServices;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FileUploadUtil {
//    @Value("${app.upload.dir}")
    private static String staticUploadDir = "src/main/resources/static/images";
    private static String temporaryUploadDir = "src/main/resources/temporary/images";
    private static String srcName = "";

    public static List<String> saveFile( List<ImageType> types,
                                MultipartFile multipartFile, boolean isProfilePic, int id) throws IOException {
        log.warn("inside save file method");
        List<String> uploadPaths = new ArrayList<>();
        List<String> locationsToSave = types.stream().map(imageType -> imageType.getImageType()).collect(Collectors.toList());
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        for(String location: locationsToSave){
            log.warn("inside for loop for save locations");
            Path staticUploadPath = Paths.get(staticUploadDir + File.separator +location);
//            Path temporaryUploadPath = Paths.get(temporaryUploadDir + File.separator +location);
            log.warn("just passed the upload dir variable");
            log.warn(staticUploadPath.toString());
//            log.warn(temporaryUploadPath.toString());
            if(isProfilePic){
                staticUploadPath = Paths.get(staticUploadPath + File.separator + String.valueOf(id));
//                temporaryUploadPath = Paths.get(temporaryUploadPath + File.separator + String.valueOf(id));
            }
            if (!Files.exists(staticUploadPath)) {
                Files.createDirectories(staticUploadPath);
            }
            try (InputStream inputStream = multipartFile.getInputStream()) {
                log.warn("in try block");
                Path staticFilePath = Paths.get(staticUploadPath + File.separator+ fileName);
//                Path temporaryFilePath = Paths.get(temporaryUploadPath + File.separator+ fileName);
                log.warn(staticFilePath.toString());
//                log.warn(temporaryFilePath.toString());
//                String srcName = Arrays.stream(filePath.toString().substring(filePath.toString().indexOf("\\images")).split("\\ ")).collect(Collectors.joining("/"));
//                String srcName = filePath.toString().substring(filePath.toString().indexOf("\\images"));
                log.warn(srcName);
                if(isProfilePic){
                    srcName = "/images/"+location+"/"+id+"/"+fileName;
                } else{
                    srcName = "/images/"+location+"/"+fileName;
                }
                uploadPaths.add(srcName);
                Files.copy(inputStream, staticFilePath, StandardCopyOption.REPLACE_EXISTING);
//                Files.copy(inputStream, temporaryFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }
        return uploadPaths;
    }

    public static void deleteFile(List<ImageType> types, MultipartFile multipartFile, boolean isProfilePic, int id) throws IOException {
        List<String> locationsToDelete = types.stream().map(imageType -> imageType.getImageType()).collect(Collectors.toList());
        String fileName = multipartFile.getOriginalFilename();
        Path deleteLocation;
        log.warn(multipartFile.toString());
        for(String location: locationsToDelete) {
            if (isProfilePic) {
                deleteLocation = Paths.get(staticUploadDir + File.separator + location + File.separator + String.valueOf(id) + File.separator + fileName);
                log.warn("is profile pic");
                log.warn("File name");
                log.warn(fileName);
            }
            else{
                deleteLocation = Paths.get(staticUploadDir + File.separator + location + File.separator + fileName);
                log.warn("is not profile pic");
            }

            log.warn(deleteLocation.toString());
            Files.deleteIfExists(deleteLocation);
        }
    }

    public static void deleteProfilePicFolder(int id) {
        Path folder = Paths.get(staticUploadDir
                +File.separator+"profilePicture"
                +File.separator+id+File.separator);
        try {
            var files = Files.newDirectoryStream(folder);
            files.forEach(path -> {
                log.warn(path.toString());
                try {
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void setNewProfilePic(MultipartFile profilePic, Member member, ImageTypeServices imageTypeServices, ImageServices imageServices, MemberServices memberServices){
        Image newProfilePic = new Image();
        String fileName = StringUtils.cleanPath(profilePic.getOriginalFilename());
        Path location = Paths.get(staticUploadDir+File.separator
                +"profilePicture"+ File.separator
                +member.getMemberId()+File.separator
                +fileName);

        newProfilePic.setImageUploadName(fileName);
        newProfilePic.setMemberId(member.getMemberId());
        ArrayList<ImageType> types = new ArrayList<>();
        types.add(imageTypeServices.getByImageType("profilePicture"));
        newProfilePic.setImageType(types);
        imageServices.save(newProfilePic);
        member.setProfilePic(newProfilePic);
        memberServices.save(member);
    }
}

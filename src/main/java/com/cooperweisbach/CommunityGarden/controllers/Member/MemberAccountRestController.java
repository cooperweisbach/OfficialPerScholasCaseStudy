package com.cooperweisbach.CommunityGarden.controllers.Member;

import com.cooperweisbach.CommunityGarden.models.*;
import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@Slf4j
public class MemberAccountRestController {

    private ConfigurationServices configurationServices;
    private MessageServices messageServices;
    private MessageThreadServices messageThreadServices;
    private ImageTypeServices imageTypeServices;
    private ImageServices imageServices;
    private MemberServices memberServices;

    @Autowired
    public MemberAccountRestController(ConfigurationServices configurationServices,
                                       MessageServices messageServices,
                                       MessageThreadServices messageThreadServices,
                                       ImageTypeServices imageTypeServices,
                                       ImageServices imageServices,
                                       MemberServices memberServices) {
        this.configurationServices = configurationServices;
        this.messageServices = messageServices;
        this.messageThreadServices = messageThreadServices;
        this.imageTypeServices = imageTypeServices;
        this.imageServices = imageServices;
        this.memberServices = memberServices;
    }

    @GetMapping("/api/configurations/load")
    public Configuration getPublishedConfiguration(){
        return configurationServices.getPublishedConfiguration();
    }

    @PostMapping("/api/messages/load")
    public Page<Message> getEnteredThreadMessages(@Param("pageNum") Integer pageNum,
                                                  @Param("numOfResults") Integer numOfResults,
                                                  @Param("threadId") Integer threadId){
            MessageThread thread = messageThreadServices.getMessageThreadById(threadId);
        return messageServices.getMessages(pageNum, numOfResults, thread);
    }

    @PostMapping("/api/images/profile-pic")
    public List<String> addPotentialProfilePicTemporarily(HttpServletRequest request, @Param("image")MultipartFile image) throws IOException {
        if(image == null){
            log.warn("Image not making it");
        }
        int id = memberServices.getMemberByEmail(request.getUserPrincipal().getName()).getMemberId();
        ImageType temporary = imageTypeServices.getByImageType("temporary");
        ArrayList<ImageType> listTemporary = new ArrayList<>();
        listTemporary.add(temporary);
        return FileUploadUtil.saveFile(listTemporary, image, true, id);
    }
}

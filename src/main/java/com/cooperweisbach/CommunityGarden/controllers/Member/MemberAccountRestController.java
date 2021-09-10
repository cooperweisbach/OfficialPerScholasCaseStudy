package com.cooperweisbach.CommunityGarden.controllers.Member;

import com.cooperweisbach.CommunityGarden.models.Configuration;
import com.cooperweisbach.CommunityGarden.models.Message;
import com.cooperweisbach.CommunityGarden.models.MessageThread;
import com.cooperweisbach.CommunityGarden.services.ConfigurationServices;
import com.cooperweisbach.CommunityGarden.services.MessageServices;
import com.cooperweisbach.CommunityGarden.services.MessageThreadServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@Slf4j
public class MemberAccountRestController {

    private ConfigurationServices configurationServices;
    private MessageServices messageServices;
    private MessageThreadServices messageThreadServices;

    @Autowired
    public MemberAccountRestController(ConfigurationServices configurationServices,
                                       MessageServices messageServices,
                                       MessageThreadServices messageThreadServices) {
        this.configurationServices = configurationServices;
        this.messageServices = messageServices;
        this.messageThreadServices = messageThreadServices;
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
}

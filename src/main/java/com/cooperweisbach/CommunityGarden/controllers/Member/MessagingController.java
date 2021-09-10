package com.cooperweisbach.CommunityGarden.controllers.Member;

import com.cooperweisbach.CommunityGarden.models.Message;
import com.cooperweisbach.CommunityGarden.models.MessageDTO;
import com.cooperweisbach.CommunityGarden.services.MemberServices;
import com.cooperweisbach.CommunityGarden.services.MessageServices;
import com.cooperweisbach.CommunityGarden.services.MessageThreadServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class MessagingController {

    private MemberServices memberServices;
    private MessageServices messageServices;
    private MessageThreadServices messageThreadServices;

    @Autowired
    public MessagingController(MemberServices memberServices,
                               MessageServices messageServices,
                               MessageThreadServices messageThreadServices) {
        this.memberServices = memberServices;
        this.messageServices = messageServices;
        this.messageThreadServices = messageThreadServices;
    }

    @MessageMapping("/chat.registerTo/{roomId}")
    @SendTo("/thread/{roomId}")
    public MessageDTO register(@DestinationVariable String roomId,@Payload MessageDTO messageDTO, SimpMessageHeaderAccessor headerAccessor){
        log.warn("Room Id register");
        log.warn(roomId);
        headerAccessor.getSessionAttributes().put("username", memberServices.getMemberById(Integer.parseInt(messageDTO.getMember())));
        return messageDTO;
    }

    @MessageMapping("/chat.sendTo/{roomId}")
    @SendTo("/thread/{roomId}")
    public MessageDTO sendMessage(@DestinationVariable String roomId, @Payload MessageDTO messageDTO){
        Message message = new Message();
        message.setMember(memberServices.getMemberById(Integer.parseInt(messageDTO.getMember())));
        message.setMessageContent(messageDTO.getContent());
        message.setMessageSent(messageDTO.getMessageSent());
        message.setMessageThread(messageThreadServices.getMessageThreadById(Integer.parseInt(messageDTO.getMessageThread())));
        Message savedMessage = messageServices.save(message);
        messageDTO.setMessageId(savedMessage.getMessageId());
        return messageDTO;
    }

}

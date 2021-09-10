package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iMessageRepo;
import com.cooperweisbach.CommunityGarden.daos.iMessageThreadRepo;
import com.cooperweisbach.CommunityGarden.models.Message;
import com.cooperweisbach.CommunityGarden.models.MessageThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class MessageServices {

    @Autowired
    private iMessageRepo messageRepo;


    public Message save(Message message){
       return messageRepo.save(message);
    }

    public void delete(Integer id){
        messageRepo.deleteById(id);
    }

    public Page<Message> getMessages(int pageNum, int numOfResults, MessageThread thread){
        return messageRepo.findMessagesByMessageThread( thread, PageRequest.of(pageNum, numOfResults, Sort.Direction.DESC, "messageId"));
    }

}

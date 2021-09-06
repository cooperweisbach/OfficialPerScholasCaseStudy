package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iMessageThreadRepo;
import com.cooperweisbach.CommunityGarden.models.MessageThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class MessageThreadServices {

    private iMessageThreadRepo messageThreadRepo;

    @Autowired
    public MessageThreadServices(iMessageThreadRepo messageThreadRepo) {
        this.messageThreadRepo = messageThreadRepo;
    }

    public List<MessageThread> getAllMessageThreads(){
        return messageThreadRepo.findAll();
    }

    public void save(MessageThread messageThread){messageThreadRepo.save(messageThread);}

    public MessageThread getMessageThreadById(Integer id) {
        return messageThreadRepo.getById(id);
    }

    public void deleteByID(int messageThreadId) {
        messageThreadRepo.deleteById(messageThreadId);
    }

    public MessageThread checkUnqiueThreadName(String messageThreadName) {
        MessageThread byName = messageThreadRepo.getMessageThreadByMessageThreadName(messageThreadName);
        if(byName == null){
            return new MessageThread();
        }
        return byName;
    }

    public MessageThread checkUnqiueThreadNameId(Integer id, String messageThreadName) {
        MessageThread byName = messageThreadRepo.getMessageThreadByMessageThreadName(messageThreadName);
        MessageThread byId = messageThreadRepo.getMessageThreadByMessageThreadId(id);
        if(byName == null){
            return byId;
        } else if(byName != byId) {
            return byName;
        } else {
            return byId;
        }

    }

    public Page<MessageThread> getMessageThreadsInRange(Integer pageNum, Integer numOfResults) {
        return messageThreadRepo.findAll(PageRequest.of(pageNum, numOfResults));
    }
}


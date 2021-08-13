package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iMessageThreadRepo;
import com.cooperweisbach.CommunityGarden.models.MessageThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
}


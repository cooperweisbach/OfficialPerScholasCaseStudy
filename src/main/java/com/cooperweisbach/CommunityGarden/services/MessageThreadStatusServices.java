package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iMessageThreadStatusRepo;
import com.cooperweisbach.CommunityGarden.models.MessageThreadStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class MessageThreadStatusServices {
    private iMessageThreadStatusRepo messageThreadStatusRepo;

    @Autowired
    public MessageThreadStatusServices(iMessageThreadStatusRepo messageThreadStatusRepo) {
        this.messageThreadStatusRepo = messageThreadStatusRepo;
    }

    public List<MessageThreadStatus> getEveryMessageThreadStatus(){
        return messageThreadStatusRepo.findAll();
    }
}

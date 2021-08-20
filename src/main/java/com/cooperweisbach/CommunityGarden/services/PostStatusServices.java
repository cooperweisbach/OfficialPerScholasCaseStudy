package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iPostStatusRepo;
import com.cooperweisbach.CommunityGarden.models.Post;
import com.cooperweisbach.CommunityGarden.models.PostStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class PostStatusServices {

    private iPostStatusRepo postStatusRepo;

    @Autowired
    public PostStatusServices(iPostStatusRepo postStatusRepo) {
        this.postStatusRepo = postStatusRepo;
    }

    public List<PostStatus> findAll(){
        return postStatusRepo.findAll();
    }
}

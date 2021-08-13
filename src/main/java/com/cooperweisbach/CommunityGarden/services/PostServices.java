package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iPostRepo;
import com.cooperweisbach.CommunityGarden.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class PostServices {

    private iPostRepo postRepo;

    @Autowired
    public PostServices(iPostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public List<Post> getAllPosts(){
        return postRepo.findAll();
    }
}

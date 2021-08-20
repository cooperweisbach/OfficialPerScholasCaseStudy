package com.cooperweisbach.CommunityGarden.services;


import com.cooperweisbach.CommunityGarden.daos.iPostTagRepo;
import com.cooperweisbach.CommunityGarden.models.Post;
import com.cooperweisbach.CommunityGarden.models.PostTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class PostTagServices {

    private iPostTagRepo postTagRepo;

    @Autowired
    public PostTagServices(iPostTagRepo postTagRepo) {
        this.postTagRepo = postTagRepo;
    }

    public List<PostTag> findAll(){
        return postTagRepo.findAll();
    }

    public Post savePostTagsFromList(String[] ofPostTags){
        List<PostTag> successfullyAdded = new ArrayList<>();
        List<String> currentlyAvailable = postTagRepo.findAll().stream().map(postTag -> postTag.getPostTagTitle().toLowerCase()).collect(Collectors.toList());
        log.warn(String.valueOf(ofPostTags.length));
        for(String pt: ofPostTags){
            log.warn(pt);
            if(!currentlyAvailable.contains(pt.toLowerCase())){
                PostTag tag = new PostTag(pt);
                log.warn(tag.getPostTagTitle());
                postTagRepo.save(tag);
                successfullyAdded.add(tag);
            }
        }
        Post returnPost = new Post();
        returnPost.setPostTagList(successfullyAdded);
        return returnPost;
    };
}

package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iPostRepo;
import com.cooperweisbach.CommunityGarden.models.Post;
import com.cooperweisbach.CommunityGarden.models.PostTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
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

    public Post checkUniquePostTitle(String title) {
        Post byTitle = postRepo.findByPostTitle(title);
        if(byTitle == null){
            log.warn("returned new post");
            return new Post();
        }
        log.warn("returned database post");
        return byTitle;
    }

    public Post checkUniquePostTitleId(int id, String title){
        Post byTitle = postRepo.findByPostTitle(title);
        Post byId = postRepo.findByPostId(id);
        if(byTitle == null){
            return byId;
        } else if(byTitle != byId){
            return byTitle;
        } else{
            return byId;
        }

    }

    public void save(Post post){
        postRepo.save(post);
    }

    public Post getPostById(int id){return postRepo.getById(id);}

    public void deletePostById(int id){postRepo.deleteByPostId(id);}

    public Post getLatestPublishedPost(){
        if(postRepo.getAllByPostStatus_PostStatus("published").isPresent()){
            return postRepo.getAllByPostStatus_PostStatus("published").stream().max(Comparator.comparing(Post::getPostId)).get();
        } else{
            return null;
        }
    }

    public Page<Post> getPostsInRange(Integer pageNum, Integer numOfResults) {
        return postRepo.findAll(PageRequest.of(pageNum, numOfResults)); 
    }
}

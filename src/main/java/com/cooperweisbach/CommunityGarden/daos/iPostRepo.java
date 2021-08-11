package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface iPostRepo extends JpaRepository<Post, Integer> {
    List<Post> findAll();
    Optional<Post> getAllByMember(Member member);
    List<Post> findAllTop();
    List<Post> getAllByPostTagList_PostTagTitle(String postTagTitle);

}

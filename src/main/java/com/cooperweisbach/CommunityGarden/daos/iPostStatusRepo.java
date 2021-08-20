package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.Post;
import com.cooperweisbach.CommunityGarden.models.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface iPostStatusRepo extends JpaRepository<PostStatus, Integer> {
    void deleteByPostStatusId(Integer integer);
}

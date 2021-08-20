package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iPostTagRepo extends JpaRepository<PostTag, Integer> {

    void deletePostTagsByPostTagTitle(String postTagTitle);
    PostTag findByPostTagTitle(String title);
}

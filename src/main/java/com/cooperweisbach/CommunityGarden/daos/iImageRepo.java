package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface iImageRepo extends JpaRepository<Image, Integer> {
    List<Image> findAllByImageStatus_ImageStatus(String imageStatus);
    Long countByImageStatus_ImageStatus(String imageStatus);

    void deleteByImageId(Integer integer);
}

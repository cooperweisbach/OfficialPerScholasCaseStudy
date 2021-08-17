package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface iImageRepo extends JpaRepository<Image, Integer> {
    List<Image> findAll();

    List<Image> findAllByImageType_ImageType(String imageType);
    Long countByImageType_ImageType(String imageType);
    void deleteByImageId(Integer integer);
}

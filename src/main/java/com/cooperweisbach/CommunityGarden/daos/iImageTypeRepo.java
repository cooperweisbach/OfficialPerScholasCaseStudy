package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface iImageTypeRepo extends JpaRepository<ImageType, Integer> {
    void deleteByImageTypeId(Integer integer);
}

package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.ImageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface iImageStatusRepo extends JpaRepository<ImageStatus, Integer> {
    void deleteByImageStatusId(Integer integer);
}

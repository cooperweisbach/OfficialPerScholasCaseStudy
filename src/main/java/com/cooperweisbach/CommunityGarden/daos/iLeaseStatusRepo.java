package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.LeaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iLeaseStatusRepo extends JpaRepository<LeaseStatus, Integer> {
    void deleteByLeaseStatusId(Integer integer);
}

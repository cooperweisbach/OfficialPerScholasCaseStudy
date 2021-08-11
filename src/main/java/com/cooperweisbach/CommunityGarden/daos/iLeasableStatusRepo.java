package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.LeasableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iLeasableStatusRepo extends JpaRepository<LeasableStatus, Integer> {

    void deleteByLeasableStatusId(Integer integer);
}

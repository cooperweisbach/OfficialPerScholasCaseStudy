package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.Leasable;
import com.cooperweisbach.CommunityGarden.models.LeasableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iLeasableStatusRepo extends JpaRepository<LeasableStatus, Integer> {
    LeasableStatus getByLeasableStatus(String name);
    void deleteByLeasableStatusId(Integer integer);
}

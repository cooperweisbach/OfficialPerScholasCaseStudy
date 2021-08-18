package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.Leasable;
import com.cooperweisbach.CommunityGarden.models.LeasableStatus;
import com.cooperweisbach.CommunityGarden.models.LeasableType;
import com.cooperweisbach.CommunityGarden.services.LeasableServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface iLeasableRepo extends JpaRepository<Leasable, Integer> {
    Leasable getLeasableByLeasableId(int id);
    Optional<Leasable> getAllByLeasableStatus_LeasableStatus(String leasableStatus);
    Optional<Leasable> getAllByLeasableType_LeasableTypeName(String leasableType);
    Leasable getByLeasableCode(String leasableCode);
    List<Leasable> findAll();
    List<Leasable> removeByLeasableCode(String leasableCode);
    Long countLeasableByLeasableType_LeasableTypeName(String leasableTypeName);

    void deleteByLeasableId(Integer integer);
}

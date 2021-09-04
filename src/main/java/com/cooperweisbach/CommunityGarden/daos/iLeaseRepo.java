package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface iLeaseRepo extends JpaRepository<Lease, Integer> {
    List<Lease> findAllByMember_MemberId(int memberId);
    Lease findByLeasable_LeasableCode(String leasableCode);
    List<Lease> findAllByLeaseStatus_LeaseStatus(String leaseStatus);

    void deleteByLeaseId(Integer integer);

    List<Lease> findAllByLeasable_LeasableCode(String leasableCode);

    List<Lease> findAllByLeasable_LeasableId(Integer leasableId);

}

package com.cooperweisbach.CommunityGarden.services;


import com.cooperweisbach.CommunityGarden.daos.iLeaseStatusRepo;
import com.cooperweisbach.CommunityGarden.models.LeasableStatus;
import com.cooperweisbach.CommunityGarden.models.LeaseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class LeaseStatusServices {
    private iLeaseStatusRepo leaseStatusRepo;

    @Autowired
    public LeaseStatusServices(iLeaseStatusRepo leaseStatusRepo) {
        this.leaseStatusRepo = leaseStatusRepo;
    }

    public List<LeaseStatus> getEveryLeaseStatus() {
        return leaseStatusRepo.findAll();
    }

    public LeaseStatus getByLeaseStatusName(String leaseStatusName){
        return leaseStatusRepo.getLeaseStatusByLeaseStatus(leaseStatusName);
    }

}

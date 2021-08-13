package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iLeaseRepo;
import com.cooperweisbach.CommunityGarden.models.Lease;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class LeaseServices {

    private iLeaseRepo leaseRepo;

    @Autowired
    public LeaseServices(iLeaseRepo leaseRepo) {
        this.leaseRepo = leaseRepo;
    }

    public List<Lease> getAllLeases(){
        return leaseRepo.findAll();
    }
}

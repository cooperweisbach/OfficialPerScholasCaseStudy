package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iLeaseRepo;
import com.cooperweisbach.CommunityGarden.daos.iLeaseStatusRepo;
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

    private final String LEASABLE_STATUS_LEASED = "leased";

    private iLeaseRepo leaseRepo;
    private iLeaseStatusRepo leaseStatusRepo;
    @Autowired
    public LeaseServices(iLeaseRepo leaseRepo, iLeaseStatusRepo leaseStatusRepo) {
        this.leaseRepo = leaseRepo;
        this.leaseStatusRepo = leaseStatusRepo;
    }

    public List<Lease> getAllLeases(){
        return leaseRepo.findAll();
    }

    public void deleteLeaseById(int id){ leaseRepo.deleteByLeaseId(id);}

    public Lease getLeaseById(int id){return leaseRepo.getById(id);}

    public void save(Lease newLease){leaseRepo.save(newLease);}


    public Lease checkUniqueLeasable(String leasable ){
        List<Lease> leases = leaseRepo.findAllByLeasable_LeasableCode(leasable);
        for(Lease l: leases) {
            if (l.getLeaseStatus().equals(LEASABLE_STATUS_LEASED)) {
                return l;
            }
        } return new Lease();
    }

    public Lease checkUniqueLeasableId(int id, String leasable){
        Lease lease = leaseRepo.getById(id);
        log.warn("Lease with id of " + lease.getLeaseId() + " made it");
        log.warn("Desired leasable code of " + leasable + " made it");
//        List<Lease> leases = leaseRepo.findAllByLeasable_LeasableCode(leasable);
//        log.warn("There are " + leases.size() + " many leases returned in lease services...");
//        if(leases.size() > 0) {
//            for(Lease l: leases){
//                log.warn("Lease Id: " + l.getLeaseId() + " Leasable Code: " + l.getLeasable().getLeasableCode());
//                if(l.getLeaseStatus().equals(LEASABLE_STATUS_LEASED)){
//                    if(l.getLeasable().getLeasableCode() == lease.getLeasable().getLeasableCode()){
//                        return lease;
//                    } else {
//                        return l;
//                    }
//                }
//            }
//        }
        return lease;
    }
}

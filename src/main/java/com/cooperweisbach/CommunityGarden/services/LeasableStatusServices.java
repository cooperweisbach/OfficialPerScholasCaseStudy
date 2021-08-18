package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iLeasableStatusRepo;
import com.cooperweisbach.CommunityGarden.models.LeasableStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class LeasableStatusServices {
    private iLeasableStatusRepo leasableStatusRepo;

    @Autowired
    public LeasableStatusServices(iLeasableStatusRepo leasableStatusRepo) {
        this.leasableStatusRepo = leasableStatusRepo;
    }

    public List<LeasableStatus> getEveryLeasableStatus(){return leasableStatusRepo.findAll();}

}

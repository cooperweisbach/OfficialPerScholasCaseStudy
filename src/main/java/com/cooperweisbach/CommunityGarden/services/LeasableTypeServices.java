package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iLeasableTypeRepo;
import com.cooperweisbach.CommunityGarden.models.LeasableType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class LeasableTypeServices {

    private iLeasableTypeRepo leasableTypeRepo;

    @Autowired
    public LeasableTypeServices(iLeasableTypeRepo leasableTypeRepo) {
        this.leasableTypeRepo = leasableTypeRepo;
    }

    public List<LeasableType> getEveryLeasableType() {return leasableTypeRepo.findAll();}
}

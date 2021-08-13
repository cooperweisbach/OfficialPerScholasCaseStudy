package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iLeasableRepo;
import com.cooperweisbach.CommunityGarden.daos.iLeasableStatusRepo;
import com.cooperweisbach.CommunityGarden.daos.iLeasableTypeRepo;
import com.cooperweisbach.CommunityGarden.models.Leasable;
import com.cooperweisbach.CommunityGarden.models.LeasableType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.TreeMap;

@Service
@Slf4j
@Transactional
public class LeasableServices {
    private iLeasableRepo leasableRepo;
    private iLeasableTypeRepo leasableTypeRepo;
    private iLeasableStatusRepo leasableStatusRepo;

    @Autowired
    public LeasableServices(iLeasableRepo leasableRepo, iLeasableTypeRepo leasableTypeRepo, iLeasableStatusRepo leasableStatusRepo) {
        this.leasableRepo = leasableRepo;
        this.leasableTypeRepo = leasableTypeRepo;
        this.leasableStatusRepo = leasableStatusRepo;
    }

     public TreeMap<String, Long> getLeasableCountsByType(){
        TreeMap<String, Long> counts = new TreeMap<>();
         List<LeasableType> types = leasableTypeRepo.findAll();
         String name;
         for(LeasableType type: types){
            name = type.getLeasableTypeName();
             counts.put(name, leasableRepo.countLeasableByLeasableType_LeasableTypeName(name));
         }
         return counts;
     }

     public List<Leasable> getAllLeasables() {
        return leasableRepo.findAll();
     }
}

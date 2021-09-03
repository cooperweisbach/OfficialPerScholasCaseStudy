package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iLeasableRepo;
import com.cooperweisbach.CommunityGarden.daos.iLeasableStatusRepo;
import com.cooperweisbach.CommunityGarden.daos.iLeasableTypeRepo;
import com.cooperweisbach.CommunityGarden.models.Leasable;
import com.cooperweisbach.CommunityGarden.models.LeasableType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    public List<Leasable> getAllByLeasableStatus(String status){
        return leasableRepo.getAllByLeasableStatus_LeasableStatus(status);
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

     public List<Leasable> getAllLeasables() {return leasableRepo.findAll();}


     public Leasable getLeasableById(int id){ return leasableRepo.getLeasableByLeasableId(id);}

    public void save(Leasable leasable){leasableRepo.save(leasable);}

    public void deleteLeasableById(int leasableId){leasableRepo.deleteByLeasableId(leasableId);}

    public Leasable checkUniqueCode(String code){
        Leasable byCode = leasableRepo.getByLeasableCode(code);
        if(byCode == null){
            return new Leasable();
        }
            return byCode;
    }
    public Leasable checkUniqueCodeId(int id, String code){
        Leasable byId = leasableRepo.getLeasableByLeasableId(id);
        Leasable byCode = leasableRepo.getByLeasableCode(code);
        if(byCode == null){
            return byId;
        } else if(byCode != byId ){
            return byCode;
        } else{
            return byId;
        }
    }

    public long count() {
       return leasableRepo.count();
    }

    public Page<Leasable> getLeasablesInRange(int pageNum, int resultSize) {
        return leasableRepo.findAll(PageRequest.of(pageNum, resultSize));
    }
}

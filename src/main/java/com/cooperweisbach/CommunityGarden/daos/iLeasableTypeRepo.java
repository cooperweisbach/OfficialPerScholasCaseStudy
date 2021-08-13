package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.LeasableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface iLeasableTypeRepo extends JpaRepository<LeasableType, Integer> {
    LeasableType getByLeasableTypeName(String leasableTypeName);
    List<LeasableType>  findAll();
    void deleteByLeasableTypeId(Integer integer);
    void deleteByLeasableTypeName(String leasableTypeName);
//    long countByLeasableTypeName(String leasableTypeName);
}

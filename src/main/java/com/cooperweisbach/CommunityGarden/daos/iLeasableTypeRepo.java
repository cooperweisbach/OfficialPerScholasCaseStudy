package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.LeasableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iLeasableTypeRepo extends JpaRepository<LeasableType, Integer> {
    void deleteByLeasableTypeId(Integer integer);
    void deleteByLeasableTypeName(String leasableTypeName);
}

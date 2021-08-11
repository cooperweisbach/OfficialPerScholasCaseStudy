package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iPaymentTypeRepo extends JpaRepository<PaymentType, Integer> {
    void deleteByPaymentTypeId(Integer integer);
}

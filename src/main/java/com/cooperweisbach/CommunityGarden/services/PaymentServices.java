package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iPaymentRepo;
import com.cooperweisbach.CommunityGarden.models.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class PaymentServices {

    private iPaymentRepo paymentRepo;

    @Autowired
    public PaymentServices(iPaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public List<Payment> getAllPayments(){
        return paymentRepo.findAll();
    }
}

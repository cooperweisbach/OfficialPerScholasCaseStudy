package com.cooperweisbach.CommunityGarden;

import com.cooperweisbach.CommunityGarden.daos.*;
import com.cooperweisbach.CommunityGarden.models.LeasableStatus;
import com.cooperweisbach.CommunityGarden.models.LeaseStatus;
import com.cooperweisbach.CommunityGarden.models.Payment;
import com.cooperweisbach.CommunityGarden.services.MemberServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@Transactional
public class AppRunner implements CommandLineRunner {
    // autowire all repo's/services
    private iImageRepo imageRepo;
    private iImageStatusRepo imageStatusRepo;
    private iLeasableRepo leasableRepo;
    private iLeasableStatusRepo leasableStatusRepo;
    private iLeasableTypeRepo leasableTypeRepo;
    private iLeaseRepo leaseRepo;
    private iLeaseStatusRepo leaseStatusRepo;
    private iMemberRepo memberRepo;
    private iMemberStatusRepo memberStatusRepo;
    private iMessageRepo messageRepo;
    private iMessageThreadRepo messageThreadRepo;
    private iMessageThreadStatusRepo messageThreadStatusRepo;
    private iPaymentRepo paymentRepo;
    private iPaymentTypeRepo paymentTypeRepo;
    private iPostRepo postRepo;
    private iPostStatusRepo postStatusRepo;
    private iPostTagRepo postTagRepo;
    private iUserRolesRepo userRolesRepo;

    public AppRunner(iImageRepo imageRepo,
                     iImageStatusRepo imageStatusRepo,
                     iLeasableRepo leasableRepo,
                     iLeasableStatusRepo leasableStatusRepo,
                     iLeasableTypeRepo leasableTypeRepo,
                     iLeaseRepo leaseRepo,
                     iLeaseStatusRepo leaseStatusRepo,
                     iMemberRepo memberRepo,
                     iMemberStatusRepo memberStatusRepo,
                     iMessageRepo messageRepo,
                     iMessageThreadRepo messageThreadRepo,
                     iMessageThreadStatusRepo messageThreadStatusRepo,
                     iPaymentRepo paymentRepo,
                     iPaymentTypeRepo paymentTypeRepo,
                     iPostRepo postRepo,
                     iPostStatusRepo postStatusRepo,
                     iPostTagRepo postTagRepo,
                     iUserRolesRepo userRolesRepo) {
        this.imageRepo = imageRepo;
        this.imageStatusRepo = imageStatusRepo;
        this.leasableRepo = leasableRepo;
        this.leasableStatusRepo = leasableStatusRepo;
        this.leasableTypeRepo = leasableTypeRepo;
        this.leaseRepo = leaseRepo;
        this.leaseStatusRepo = leaseStatusRepo;
        this.memberRepo = memberRepo;
        this.memberStatusRepo = memberStatusRepo;
        this.messageRepo = messageRepo;
        this.messageThreadRepo = messageThreadRepo;
        this.messageThreadStatusRepo = messageThreadStatusRepo;
        this.paymentRepo = paymentRepo;
        this.paymentTypeRepo = paymentTypeRepo;
        this.postRepo = postRepo;
        this.postStatusRepo = postStatusRepo;
        this.postTagRepo = postTagRepo;
        this.userRolesRepo = userRolesRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        // add dummy data


    }


}

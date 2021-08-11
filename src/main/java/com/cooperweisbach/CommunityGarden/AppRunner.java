package com.cooperweisbach.CommunityGarden;

import com.cooperweisbach.CommunityGarden.daos.*;
import com.cooperweisbach.CommunityGarden.models.*;
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
        //Add statuses, types, and user roles
        leasableTypeRepo.save(new LeasableType("outdoorGardenPlot"));
        leasableTypeRepo.save(new LeasableType("beeHive"));
        leasableTypeRepo.save(new LeasableType("workshop"));
        leasableTypeRepo.save(new LeasableType("greenhousePlot"));
        leasableTypeRepo.save(new LeasableType("fishTank"));
        leasableStatusRepo.save(new LeasableStatus("open"));
        leasableStatusRepo.save(new LeasableStatus("closed"));
        leasableStatusRepo.save(new LeasableStatus("leased"));
        leaseStatusRepo.save(new LeaseStatus("active"));
        leaseStatusRepo.save(new LeaseStatus("expired"));
        memberStatusRepo.save(new MemberStatus("active"));
        memberStatusRepo.save(new MemberStatus("inactive"));
        messageThreadStatusRepo.save(new MessageThreadStatus("open"));
        messageThreadStatusRepo.save(new MessageThreadStatus("closed"));
        paymentTypeRepo.save(new PaymentType("newMemberPayment"));
        paymentTypeRepo.save(new PaymentType("leasePayment"));
        paymentTypeRepo.save(new PaymentType("donation"));
        postStatusRepo.save(new PostStatus("published"));
        postStatusRepo.save(new PostStatus("inProgress"));
        postStatusRepo.save(new PostStatus("element"));
        userRolesRepo.save(new UserRoles("head"));
        userRolesRepo.save(new UserRoles("admin"));
        userRolesRepo.save(new UserRoles("adminRead"));
        userRolesRepo.save(new UserRoles("adminReadWrite"));
        userRolesRepo.save(new UserRoles("member"));
        imageStatusRepo.save(new ImageStatus("defaultProfilePicture"));
        imageStatusRepo.save(new ImageStatus("profilePicture"));
        imageStatusRepo.save(new ImageStatus("postImage"));
        imageStatusRepo.save(new ImageStatus("slideShowHP"));
        imageStatusRepo.save(new ImageStatus("gallery"));








        //Adding members



    }


}

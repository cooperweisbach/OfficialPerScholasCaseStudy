package com.cooperweisbach.CommunityGarden;

import com.cooperweisbach.CommunityGarden.daos.*;
import com.cooperweisbach.CommunityGarden.models.*;
import com.cooperweisbach.CommunityGarden.services.MemberServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Component
@Transactional
public class AppRunner implements CommandLineRunner {
    // autowire all repo's/services
    private iImageRepo imageRepo;
    private iImageTypeRepo imageTypeRepo;
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

    @Autowired
    public AppRunner(
                     iImageRepo imageRepo,
                     iImageTypeRepo imageTypeRepo,
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
        this.imageTypeRepo = imageTypeRepo;
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
        leaseStatusRepo.save(new LeaseStatus("terminated early"));
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
        userRolesRepo.save(new UserRoles("dev"));
        userRolesRepo.save(new UserRoles("head"));
        userRolesRepo.save(new UserRoles("admin"));
        userRolesRepo.save(new UserRoles("adminRead"));
        userRolesRepo.save(new UserRoles("adminReadWrite"));
        userRolesRepo.save(new UserRoles("member"));
        imageTypeRepo.save(new ImageType("defaultProfilePicture"));
        imageTypeRepo.save(new ImageType("profilePicture"));
        imageTypeRepo.save(new ImageType("postImage"));
        imageTypeRepo.save(new ImageType("slideShowHP"));
        imageTypeRepo.save(new ImageType("gallery"));



        //Adding members
        Member member = new Member("Cooper", "W", "test1@gmail.com", "1234567890", "PASSWORD");
        memberRepo.save(member);
        member.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("head"));
        Member member1 = new Member("John", "S", "test2@gmail.com", "1234567890", "PASSWORD");
        memberRepo.save(member1);
        member1.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("member"));
        Member member2 = new Member("Mary Ann", "D", "test3@gmail.com", "1234567890", "PASSWORD");
        memberRepo.save(member2);
        member2.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("member"));
        Member member3 = new Member("Derek", "Q", "test4@gmail.com", "1234567890", "PASSWORD");
        memberRepo.save(member3);
        member3.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("admin"));
        Member member4 = new Member("Susan", "T", "test5@gmail.com", "1234567890", "PASSWORD");
        memberRepo.save(member4);
        member4.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("dev"));
        Member member5 = new Member("Graham", "Z", "test6@gmail.com", "1234567890", "PASSWORD");
        memberRepo.save(member5);
        member5.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("member"));

        //Adding leasables
        Leasable leasable;
        Object[][] l = {{"OP1",120.0D,50.0D, "outdoorGardenPlot"},
                        {"OP2",120.0D,50.0D, "outdoorGardenPlot"},
                        {"W1",120.0D,50.0D, "workshop"},
                        {"W2",120.0D,50.0D, "workshop"},
                        {"FT1",120.0D,50.0D, "fishTank"},
                        {"FT2",120.0D,50.0D, "fishTank"},
                        {"GH1",120.0D,50.0D, "greenhousePlot"},
                        {"GH2",120.0D,50.0D, "greenhousePlot"},
                        {"BH1", 120.0D,50.0D, "beeHive"},
                        {"BH2", 120.0D,50.0D, "beeHive"}};
        for(int i =0; i < l.length; i++ ){
            leasable = new Leasable((String)l[i][0], (double)l[i][1],(double)l[i][2]);
            leasableRepo.save(leasable);
            leasable.setLeasableType(leasableTypeRepo.getByLeasableTypeName((String)l[i][3]));
            leasable.setLeasableStatus(leasableStatusRepo.getByLeasableStatus("open"));
        }

//        PostTag postTag;
//        Object[][] pt = {{"beekeeping"},{"gardening"},{"germinating"}};
//
//        Post post;
//        Object[][] p = {{"Getting Started With Beekeeping","Some Content"},
//                        {"Gardening in Arizona Summers","Some more content"},
//                        {"Germinating your Sunflower Seeds","Even more content"}};

        Lease lease;
        Object[][] ll = {{1, 1, 1},
                         {1, 2, 1}};

        for(int i=0; i<ll.length; i++){
            lease = new Lease();
            lease.setMember(memberRepo.getByMemberId((int)ll[i][0]));
            lease.setLeasable(leasableRepo.getLeasableByLeasableId((int)ll[i][1]));
            lease.setLeaseStatus(leaseStatusRepo.getById((int)ll[i][2]));
            leaseRepo.save(lease);
            leasableRepo.getLeasableByLeasableId((int)ll[i][1]).setLeasableStatus(leasableStatusRepo.getByLeasableStatus("leased"));
        }

//
//        MessageThread messageThread;
//        Object[][] mt = {{}};
//
//        Payment payment;
//        Object[][] pm = {{}};
//
//        Image image;
//        Object[][] i = {{}};


//


    }


}

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
import java.text.SimpleDateFormat;
import java.util.*;

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
    private iConfigurationRepo configurationRepo;

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
                     iUserRolesRepo userRolesRepo,
                     iConfigurationRepo configurationRepo) {
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
        this.configurationRepo =  configurationRepo;
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
        messageThreadStatusRepo.save(new MessageThreadStatus("archived"));
        messageThreadStatusRepo.save(new MessageThreadStatus("closed"));
        paymentTypeRepo.save(new PaymentType("newMemberPayment"));
        paymentTypeRepo.save(new PaymentType("leasePayment"));
        paymentTypeRepo.save(new PaymentType("donation"));
        postStatusRepo.save(new PostStatus("published"));
        postStatusRepo.save(new PostStatus("inProgress"));
        postStatusRepo.save(new PostStatus("element"));
        userRolesRepo.save(new UserRoles("ROLE_DEV"));
        userRolesRepo.save(new UserRoles("ROLE_HEAD"));
        userRolesRepo.save(new UserRoles("ROLE_ADMIN"));
        userRolesRepo.save(new UserRoles("ROLE_MEMBER"));
        imageTypeRepo.save(new ImageType("defaultProfilePicture"));
        imageTypeRepo.save(new ImageType("profilePicture"));
        imageTypeRepo.save(new ImageType("postImage"));
        imageTypeRepo.save(new ImageType("slideShowHP"));
        imageTypeRepo.save(new ImageType("gallery"));
        imageTypeRepo.save(new ImageType("temporary"));
        postTagRepo.save(new PostTag("gardening"));
        postTagRepo.save(new PostTag("beekeeping"));
        postTagRepo.save(new PostTag("greenhouse"));
        postTagRepo.save(new PostTag("arizonaVeggies"));


        //Adding default profile pic
        Image defaultProf = new Image();
        defaultProf.setImageUploadName("default.png");
        imageRepo.save(defaultProf);
        List<ImageType> imageTypes = new ArrayList<>();
        imageTypes.add(imageTypeRepo.getById(1));
        defaultProf.setImageType(imageTypes);
        Image defaultProf2 = new Image();
        defaultProf2.setImageUploadName("gitHubProfile.jpg");
        imageRepo.save(defaultProf2);
        List<ImageType> imageTypes2 = new ArrayList<>();
        imageTypes2.add(imageTypeRepo.getById(1));
        defaultProf2.setImageType(imageTypes2);

        //Adding members
        SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy");
        Member member = new Member("Cooper", "W", "test1@gmail.com", "1234567890", "$2a$04$9DCAsuRa7w38vZJJnAZkXOa22mDaYPom0/kutj69ov3Wqoeg58e/6");
        memberRepo.save(member);
        member.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("ROLE_ADMIN"));
        member.setMemberStatus(memberStatusRepo.getById(1));
        member.setProfilePic(imageRepo.getById(1));
        member.setJoinedDate(sdf.parse("11-02-2020"));
        Member member1 = new Member("John", "S", "test2@gmail.com", "1234567890", "$2a$04$BnWzlg2HUJCZWzJK15fr3.dzuKJ/tyOdwvojpGzDpmL5Yg5sqa9WW");
        member1.setProfilePic(imageRepo.getById(1));
        memberRepo.save(member1);
        member1.setMemberStatus(memberStatusRepo.getById(1));
        member1.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("ROLE_ADMIN"));
        member1.setJoinedDate(sdf.parse("12-01-2020"));
        Member member2 = new Member("Mary Ann", "D", "test3@gmail.com", "1234567890", "$2a$04$BnWzlg2HUJCZWzJK15fr3.dzuKJ/tyOdwvojpGzDpmL5Yg5sqa9WW");
        memberRepo.save(member2);
        member2.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("ROLE_MEMBER"));
        member2.setMemberStatus(memberStatusRepo.getById(1));
        member2.setProfilePic(imageRepo.getById(1));
        member2.setJoinedDate(sdf.parse("01-02-2021"));
        Member member3 = new Member("Derek", "Q", "test4@gmail.com", "1234567890", "$2a$04$BnWzlg2HUJCZWzJK15fr3.dzuKJ/tyOdwvojpGzDpmL5Yg5sqa9WW");
        memberRepo.save(member3);
        member3.setMemberStatus(memberStatusRepo.getById(1));
        member3.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("ROLE_MEMBER"));
        member3.setProfilePic(imageRepo.getById(1));
        member3.setJoinedDate(sdf.parse("02-13-2021"));
        Member member4 = new Member("Susan", "T", "test5@gmail.com", "1234567890", "$2a$04$BnWzlg2HUJCZWzJK15fr3.dzuKJ/tyOdwvojpGzDpmL5Yg5sqa9WW");
        memberRepo.save(member4);
        member4.setMemberStatus(memberStatusRepo.getById(1));
        member4.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("ROLE_MEMBER"));
        member4.setProfilePic(imageRepo.getById(1));
        member4.setJoinedDate(sdf.parse("03-1-2021"));
        Member member5 = new Member("Graham", "Z", "test6@gmail.com", "1234567890", "$2a$04$BnWzlg2HUJCZWzJK15fr3.dzuKJ/tyOdwvojpGzDpmL5Yg5sqa9WW");
        memberRepo.save(member5);
        member5.setMemberStatus(memberStatusRepo.getById(1));
        member5.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("ROLE_MEMBER"));
        member5.setProfilePic(imageRepo.getById(1));
        member5.setJoinedDate(sdf.parse("03-1-2021"));
        Member member6 = new Member("Graham", "Z", "test7@gmail.com", "1234567890", "$2a$04$BnWzlg2HUJCZWzJK15fr3.dzuKJ/tyOdwvojpGzDpmL5Yg5sqa9WW");
        memberRepo.save(member6);
        member6.setMemberStatus(memberStatusRepo.getById(1));
        member6.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("ROLE_MEMBER"));
        member6.setProfilePic(imageRepo.getById(1));
        member6.setJoinedDate(sdf.parse("04-1-2021"));
        Member member7 = new Member("Graham", "Z", "test8@gmail.com", "1234567890", "$2a$04$BnWzlg2HUJCZWzJK15fr3.dzuKJ/tyOdwvojpGzDpmL5Yg5sqa9WW");
        memberRepo.save(member7);
        member7.setMemberStatus(memberStatusRepo.getById(1));
        member7.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("ROLE_MEMBER"));
        member7.setProfilePic(imageRepo.getById(1));
        member7.setJoinedDate(sdf.parse("08-1-2021"));
        Member member8 = new Member("Graham", "Z", "test9@gmail.com", "1234567890", "$2a$04$BnWzlg2HUJCZWzJK15fr3.dzuKJ/tyOdwvojpGzDpmL5Yg5sqa9WW");
        memberRepo.save(member8);
        member8.setMemberStatus(memberStatusRepo.getById(1));
        member8.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("ROLE_MEMBER"));
        member8.setProfilePic(imageRepo.getById(1));
        member8.setJoinedDate(sdf.parse("09-1-2021"));
        Member member9 = new Member("Graham", "Z", "test10@gmail.com", "1234567890", "$2a$04$BnWzlg2HUJCZWzJK15fr3.dzuKJ/tyOdwvojpGzDpmL5Yg5sqa9WW");
        memberRepo.save(member9);
        member9.setMemberStatus(memberStatusRepo.getById(1));
        member9.setUserRoles(userRolesRepo.getUserRolesByUserRoleName("ROLE_MEMBER"));
        member9.setProfilePic(imageRepo.getById(1));


        MessageThread newthread = new MessageThread();
        newthread.setMessageThreadName("Welcome");
        newthread.setMessageThreadStatus(messageThreadStatusRepo.getById(1));
        messageThreadRepo.save(newthread);
        newthread.setThreadDescription("Community garden member's code of conduct and roadmap!! If you're new, start here...");

        MessageThread newthread2 = new MessageThread();
        newthread2.setMessageThreadName("General Announcements");
        newthread2.setMessageThreadStatus(messageThreadStatusRepo.getById(1));
        messageThreadRepo.save(newthread2);
        newthread2.setThreadDescription("Come here to stay up to date about new activities, policies, and everything else!");

        MessageThread newthread3 = new MessageThread();
        newthread3.setMessageThreadName("Charity");
        newthread3.setMessageThreadStatus(messageThreadStatusRepo.getById(1));
        messageThreadRepo.save(newthread3);
//        newthread.setThreadDescription("Come here to stay up to date about new activities, policies, and everything else!");

        MessageThread newthread4 = new MessageThread();
        newthread4.setMessageThreadName("Classes");
        newthread4.setMessageThreadStatus(messageThreadStatusRepo.getById(1));
        messageThreadRepo.save(newthread4);

        MessageThread newthread5 = new MessageThread();
        newthread5.setMessageThreadName("Farmer's Market");
        newthread5.setMessageThreadStatus(messageThreadStatusRepo.getById(1));
        messageThreadRepo.save(newthread5);



        //Adding leasables
        Leasable leasable;
        Object[][] l = {{"OP1",120.0D,50.0D, "outdoorGardenPlot"},
                        {"OP2",120.0D,50.0D, "outdoorGardenPlot"},
                        {"OP3",120.0D,50.0D, "outdoorGardenPlot"},
                        {"OP4",120.0D,50.0D, "outdoorGardenPlot"},
                        {"OP5",120.0D,50.0D, "outdoorGardenPlot"},
                        {"OP6",120.0D,50.0D, "outdoorGardenPlot"},
                        {"OP7",120.0D,50.0D, "outdoorGardenPlot"},
                        {"OP8",120.0D,50.0D, "outdoorGardenPlot"},
                        {"OP9",120.0D,50.0D, "outdoorGardenPlot"},
                        {"OP10",120.0D,50.0D, "outdoorGardenPlot"},
                        {"OP11",120.0D,50.0D, "outdoorGardenPlot"},
                        {"OP12",120.0D,50.0D, "outdoorGardenPlot"},
                        {"OP13",120.0D,50.0D, "outdoorGardenPlot"},
                        {"W1",120.0D,50.0D, "workshop"},
                        {"W2",120.0D,50.0D, "workshop"},
                        {"W3",120.0D,50.0D, "workshop"},
                        {"W4",120.0D,50.0D, "workshop"},
                        {"W5",120.0D,50.0D, "workshop"},
                        {"W6",120.0D,50.0D, "workshop"},
                        {"W7",120.0D,50.0D, "workshop"},
                        {"W8",120.0D,50.0D, "workshop"},
                        {"W9",120.0D,50.0D, "workshop"},
                        {"W10",120.0D,50.0D, "workshop"},
                        {"FT1",120.0D,50.0D, "fishTank"},
                        {"FT2",120.0D,50.0D, "fishTank"},
                        {"FT3",120.0D,50.0D, "fishTank"},
                        {"FT4",120.0D,50.0D, "fishTank"},
                        {"FT5",120.0D,50.0D, "fishTank"},
                        {"FT6",120.0D,50.0D, "fishTank"},
                        {"FT7",120.0D,50.0D, "fishTank"},
                        {"FT8",120.0D,50.0D, "fishTank"},
                        {"GH1",120.0D,50.0D, "greenhousePlot"},
                        {"GH2",120.0D,50.0D, "greenhousePlot"},
                        {"GH3",120.0D,50.0D, "greenhousePlot"},
                        {"GH4",120.0D,50.0D, "greenhousePlot"},
                        {"GH5",120.0D,50.0D, "greenhousePlot"},
                        {"GH6",120.0D,50.0D, "greenhousePlot"},
                        {"GH7",120.0D,50.0D, "greenhousePlot"},
                        {"GH8",120.0D,50.0D, "greenhousePlot"},
                        {"GH9",120.0D,50.0D, "greenhousePlot"},
                        {"BH1", 120.0D,50.0D, "beeHive"},
                        {"BH2", 120.0D,50.0D, "beeHive"},
                        {"BH3", 120.0D,50.0D, "beeHive"},
                        {"BH4", 120.0D,50.0D, "beeHive"},
                        {"BH5", 120.0D,50.0D, "beeHive"},
                        {"BH6", 120.0D,50.0D, "beeHive"},
                        {"BH7", 120.0D,50.0D, "beeHive"}};
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

//    imageRepo.save(new Image());

    configurationRepo.save(new Configuration("August2021",
            "{\"version\":\"4.5.0\",\"objects\":[{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":356.57,\"top\":0,\"width\":20,\"height\":20,\"fill\":\"transparent\",\"stroke\":\"grey\",\"strokeWidth\":0.5,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":true,\"strokeMiterLimit\":4,\"scaleX\":7.39,\"scaleY\":12.21,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"class\":\"extraRect\"},{\"type\":\"textbox\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":404.38,\"top\":75.7,\"width\":62.22,\"height\":48.82,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"text\":\"Parking Lot A\",\"fontSize\":20,\"fontWeight\":\"normal\",\"fontFamily\":\"Times New Roman\",\"fontStyle\":\"normal\",\"lineHeight\":1.16,\"underline\":false,\"overline\":false,\"linethrough\":false,\"textAlign\":\"left\",\"textBackgroundColor\":\"\",\"charSpacing\":0,\"path\":null,\"direction\":\"ltr\",\"minWidth\":20,\"splitByGrapheme\":false,\"class\":\"extraText\",\"styles\":{}},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":324.5,\"top\":367.73,\"width\":20,\"height\":20,\"fill\":\"transparent\",\"stroke\":\"grey\",\"strokeWidth\":0.5,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":true,\"strokeMiterLimit\":4,\"scaleX\":8.68,\"scaleY\":6.82,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"class\":\"extraRect\"},{\"type\":\"textbox\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":387.45,\"top\":424.3,\"width\":80.76,\"height\":48.82,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"text\":\"Farmer's Market\",\"fontSize\":20,\"fontWeight\":\"normal\",\"fontFamily\":\"Times New Roman\",\"fontStyle\":\"normal\",\"lineHeight\":1.16,\"underline\":false,\"overline\":false,\"linethrough\":false,\"textAlign\":\"left\",\"textBackgroundColor\":\"\",\"charSpacing\":0,\"path\":null,\"direction\":\"ltr\",\"minWidth\":20,\"splitByGrapheme\":false,\"class\":\"extraText\",\"styles\":{}},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":322.71,\"top\":245.02,\"width\":20,\"height\":20,\"fill\":\"transparent\",\"stroke\":\"grey\",\"strokeWidth\":0.5,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":true,\"strokeMiterLimit\":4,\"scaleX\":8.98,\"scaleY\":6.1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"class\":\"extraRect\"},{\"type\":\"textbox\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":383.47,\"top\":294.82,\"width\":64.42,\"height\":22.6,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"text\":\"Nursery\",\"fontSize\":20,\"fontWeight\":\"normal\",\"fontFamily\":\"Times New Roman\",\"fontStyle\":\"normal\",\"lineHeight\":1.16,\"underline\":false,\"overline\":false,\"linethrough\":false,\"textAlign\":\"left\",\"textBackgroundColor\":\"\",\"charSpacing\":0,\"path\":null,\"direction\":\"ltr\",\"minWidth\":20,\"splitByGrapheme\":false,\"class\":\"extraText\",\"styles\":{}},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":-3.98,\"top\":-4.99,\"width\":20,\"height\":20,\"fill\":\"transparent\",\"stroke\":\"grey\",\"strokeWidth\":0.5,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":true,\"strokeMiterLimit\":4,\"scaleX\":6.28,\"scaleY\":6.28,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"class\":\"extraRect\"},{\"type\":\"textbox\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":9.96,\"top\":52.79,\"width\":96.63,\"height\":22.6,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"text\":\"Renewables\",\"fontSize\":20,\"fontWeight\":\"normal\",\"fontFamily\":\"Times New Roman\",\"fontStyle\":\"normal\",\"lineHeight\":1.16,\"underline\":false,\"overline\":false,\"linethrough\":false,\"textAlign\":\"left\",\"textBackgroundColor\":\"\",\"charSpacing\":0,\"path\":null,\"direction\":\"ltr\",\"minWidth\":20,\"splitByGrapheme\":false,\"class\":\"extraText\",\"styles\":{}},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":199.2,\"top\":245.02,\"width\":20,\"height\":20,\"fill\":\"transparent\",\"stroke\":\"grey\",\"strokeWidth\":0.5,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":true,\"strokeMiterLimit\":4,\"scaleX\":4.4,\"scaleY\":12.58,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"class\":\"extraRect\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":216.14,\"top\":1,\"width\":20,\"height\":20,\"fill\":\"transparent\",\"stroke\":\"grey\",\"strokeWidth\":0.5,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":true,\"strokeMiterLimit\":4,\"scaleX\":6.24,\"scaleY\":11.24,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"class\":\"extraRect\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":11.95,\"top\":253.98,\"width\":20,\"height\":20,\"fill\":\"transparent\",\"stroke\":\"grey\",\"strokeWidth\":0.5,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":true,\"strokeMiterLimit\":4,\"scaleX\":7.77,\"scaleY\":12.04,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"class\":\"extraRect\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":11.95,\"top\":136.45,\"width\":20,\"height\":20,\"fill\":\"transparent\",\"stroke\":\"grey\",\"strokeWidth\":0.5,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":true,\"strokeMiterLimit\":4,\"scaleX\":8.63,\"scaleY\":4.96,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"class\":\"extraRect\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":209.16,\"top\":253.98,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"OP1\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":251.99,\"top\":254.98,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"OP2\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":209.16,\"top\":287.85,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"OP3\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":251.99,\"top\":288.84,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"OP4\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":210.16,\"top\":327.69,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"OP5\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":251.99,\"top\":326.69,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"OP6\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":211.16,\"top\":368.53,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"OP7\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":251,\"top\":368.53,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"OP8\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":251,\"top\":407.37,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"OP10\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":210.16,\"top\":407.37,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"OP9\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":211.16,\"top\":444.22,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"OP11\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":251,\"top\":445.22,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"OP12\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":23.9,\"top\":149.4,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"W1\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":71.71,\"top\":148.41,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"W2\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":120.52,\"top\":148.41,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"W3\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":23.9,\"top\":190.24,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"W4\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":71.71,\"top\":190.24,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"W5\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":121.51,\"top\":189.24,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"W6\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":22.91,\"top\":455.18,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"GH1\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":22.91,\"top\":401.39,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"GH2\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":26.89,\"top\":354.58,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"GH3\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":27.89,\"top\":308.76,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"GH4\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":29.88,\"top\":268.92,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"GH5\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":104.58,\"top\":268.92,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"GH6\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":102.59,\"top\":334.66,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"GH7\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":104.58,\"top\":389.44,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"GH8\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":99.6,\"top\":456.18,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"GH9\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":230.08,\"top\":13.94,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"BH1\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":230.08,\"top\":64.74,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"BH2\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":230.08,\"top\":122.51,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"BH3\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":229.08,\"top\":184.26,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"BH4\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":280.88,\"top\":13.94,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"FT1\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":283.86,\"top\":63.75,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"FT2\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":284.86,\"top\":124.5,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"FT3\"},{\"type\":\"rect\",\"version\":\"4.5.0\",\"originX\":\"left\",\"originY\":\"top\",\"left\":282.87,\"top\":183.27,\"width\":20,\"height\":20,\"fill\":\"rgb(0,0,0)\",\"stroke\":null,\"strokeWidth\":1,\"strokeDashArray\":null,\"strokeLineCap\":\"butt\",\"strokeDashOffset\":0,\"strokeLineJoin\":\"miter\",\"strokeUniform\":false,\"strokeMiterLimit\":4,\"scaleX\":1,\"scaleY\":1,\"angle\":0,\"flipX\":false,\"flipY\":false,\"opacity\":1,\"shadow\":null,\"visible\":true,\"backgroundColor\":\"\",\"fillRule\":\"nonzero\",\"paintFirst\":\"fill\",\"globalCompositeOperation\":\"source-over\",\"skewX\":0,\"skewY\":0,\"rx\":0,\"ry\":0,\"id\":\"FT8\"}]}",
                                true));
        //
        //{"version":"4.5.0","objects":[{"type":"rect","version":"4.5.0","originX":"left","originY":"top","left":142.43,"top":150.4,"width":20,"height":20,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":false,"strokeMiterLimit":4,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"rx":0,"ry":0,"id":"OP1"},{"type":"rect","version":"4.5.0","originX":"left","originY":"top","left":168.33,"top":150.4,"width":20,"height":20,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":false,"strokeMiterLimit":4,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"rx":0,"ry":0,"id":"W1"},{"type":"rect","version":"4.5.0","originX":"left","originY":"top","left":142.43,"top":177.29,"width":20,"height":20,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":false,"strokeMiterLimit":4,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"rx":0,"ry":0,"id":"FT1"},{"type":"rect","version":"4.5.0","originX":"left","originY":"top","left":168.33,"top":177.29,"width":20,"height":20,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":false,"strokeMiterLimit":4,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"rx":0,"ry":0,"id":"BH1"},{"type":"rect","version":"4.5.0","originX":"left","originY":"top","left":142.43,"top":214.14,"width":20,"height":20,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":false,"strokeMiterLimit":4,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"rx":0,"ry":0,"id":"GH1"},{"type":"rect","version":"4.5.0","originX":"left","originY":"top","left":169.32,"top":214.14,"width":20,"height":20,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":false,"strokeMiterLimit":4,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"rx":0,"ry":0,"id":"OP2"},{"type":"rect","version":"4.5.0","originX":"left","originY":"top","left":142.43,"top":241.04,"width":20,"height":20,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":false,"strokeMiterLimit":4,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"rx":0,"ry":0,"id":"FT2"},{"type":"rect","version":"4.5.0","originX":"left","originY":"top","left":169.32,"top":240.04,"width":20,"height":20,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":false,"strokeMiterLimit":4,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"rx":0,"ry":0,"id":"W2"},{"type":"rect","version":"4.5.0","originX":"left","originY":"top","left":135.46,"top":143.43,"width":20,"height":20,"fill":"transparent","stroke":"grey","strokeWidth":0.5,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":true,"strokeMiterLimit":4,"scaleX":3.02,"scaleY":6.15,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"rx":0,"ry":0,"class":"extraRect"},{"type":"rect","version":"4.5.0","originX":"left","originY":"top","left":135.46,"top":-1,"width":20,"height":20,"fill":"transparent","stroke":"grey","strokeWidth":0.5,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":true,"strokeMiterLimit":4,"scaleX":18.22,"scaleY":3.51,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"rx":0,"ry":0,"class":"extraRect"},{"type":"textbox","version":"4.5.0","originX":"left","originY":"top","left":253.98,"top":22.91,"width":168.72,"height":22.6,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":false,"strokeMiterLimit":4,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"text":"PARKING LOT A","fontSize":20,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"normal","lineHeight":1.16,"underline":false,"overline":false,"linethrough":false,"textAlign":"left","textBackgroundColor":"","charSpacing":0,"path":null,"direction":"ltr","minWidth":20,"splitByGrapheme":false,"class":"extraText","styles":{}},{"type":"rect","version":"4.5.0","originX":"left","originY":"top","left":-2.99,"top":371.51,"width":20,"height":20,"fill":"transparent","stroke":"grey","strokeWidth":0.5,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":true,"strokeMiterLimit":4,"scaleX":6.56,"scaleY":6.56,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"rx":0,"ry":0,"class":"extraRect"},{"type":"textbox","version":"4.5.0","originX":"left","originY":"top","left":21.91,"top":416.33,"width":65.57,"height":48.82,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":false,"strokeMiterLimit":4,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"text":"SOLAR FARM","fontSize":20,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"normal","lineHeight":1.16,"underline":false,"overline":false,"linethrough":false,"textAlign":"left","textBackgroundColor":"","charSpacing":0,"path":null,"direction":"ltr","minWidth":20,"splitByGrapheme":false,"class":"extraText","styles":{}},{"type":"line","version":"4.5.0","originX":"left","originY":"top","left":202.01,"top":143.41,"width":100,"height":0,"fill":"rgb(0,0,0)","stroke":"black","strokeWidth":1,"strokeDashArray":[1,1],"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":false,"strokeMiterLimit":4,"scaleX":1.26,"scaleY":4.99,"angle":89.5,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"class":"extraLine","x1":-50,"x2":50,"y1":0,"y2":0},{"type":"rect","version":"4.5.0","originX":"left","originY":"top","left":203.19,"top":143.43,"width":20,"height":20,"fill":"transparent","stroke":"grey","strokeWidth":0.5,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":true,"strokeMiterLimit":4,"scaleX":14.89,"scaleY":6.3,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"rx":0,"ry":0,"class":"extraRect"},{"type":"textbox","version":"4.5.0","originX":"left","originY":"top","left":318.73,"top":175.3,"width":81.11,"height":75.03,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeDashOffset":0,"strokeLineJoin":"miter","strokeUniform":false,"strokeMiterLimit":4,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"backgroundColor":"","fillRule":"nonzero","paintFirst":"fill","globalCompositeOperation":"source-over","skewX":0,"skewY":0,"text":"PLOTS COMING SOON","fontSize":20,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"normal","lineHeight":1.16,"underline":false,"overline":false,"linethrough":false,"textAlign":"left","textBackgroundColor":"","charSpacing":0,"path":null,"direction":"ltr","minWidth":20,"splitByGrapheme":false,"class":"extraText","styles":{}}]}

        Post post1 = new Post("Title", "Content");
        postRepo.save(post1);
        post1.setPostStatus(postStatusRepo.getById(1));
        ArrayList<PostTag> tags = new ArrayList<>();
        tags.add(postTagRepo.getById(1));
        tags.add(postTagRepo.getById(2));
        post1.setPostTagList(tags);
        post1.setMember(member);
    }



}

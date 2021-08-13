package com.cooperweisbach.CommunityGarden.controllers;

import com.cooperweisbach.CommunityGarden.models.Lease;
import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class HomeController {

    private MemberServices memberServices;
    private LeasableServices leasableServices;
    private MemberStatusServices memberStatusServices;
    private UserRolesServices userRolesServices;
    private LeaseServices leaseServices;
    private PostServices postServices;
    private ImageServices imageServices;
    private PaymentServices paymentServices;
    private MessageThreadServices messageThreadServices;

    @Autowired
    public HomeController(MemberServices memberServices,
                          LeasableServices leasableServices,
                          MemberStatusServices memberStatusServices,
                          UserRolesServices userRolesServices,
                          LeaseServices leaseServices,
                          PostServices postServices,
                          ImageServices imageServices,
                          PaymentServices paymentServices,
                          MessageThreadServices messageThreadServices) {
        this.memberServices = memberServices;
        this.leasableServices = leasableServices;
        this.memberStatusServices = memberStatusServices;
        this.userRolesServices = userRolesServices;
        this.leaseServices = leaseServices;
        this.postServices = postServices;
        this.imageServices = imageServices;
        this.paymentServices = paymentServices;
        this.messageThreadServices = messageThreadServices;
    }

    @GetMapping("/")
    public String home(Model m){
        long memberCount = memberServices.memberCount();
        log.warn(String.valueOf(memberCount));
        m.addAttribute("totalMembers", memberCount);
        m.addAttribute("typeTreeMap", (Map<String, Long>)leasableServices.getLeasableCountsByType());

//        for(Map.Entry<String, Long>   e: leasableServices.getLeasableCountsByType().entrySet()){
//            log.warn(e.getKey() + " " + e.getValue());
//            m.addAttribute(e.getKey(), e.getValue());
//        }
        return "template";
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////Admin Panel Request Handler//////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/admin")
    public String admin(Model m){
        return "admin";
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////Admin RequestHandlers To Get READ Data/////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/admin/users")
    public String adminGetAllUsers(Model m){
//        m.addAttribute("user", new Member());
        m.addAttribute("allMembers", memberServices.getAllMembers());
        m.addAttribute("memberStatuses", memberStatusServices.getEveryMemberStatus());
        m.addAttribute("userRoles", userRolesServices.getEveryUserRole());
        return "admin/members";
    }
    @GetMapping("/admin/leasables")
    public String adminGetAllLeasables(Model m){
        m.addAttribute("allLeasables", leasableServices.getAllLeasables());
        return "admin/leasables";
    }
    @GetMapping("/admin/leases")
    public String adminGetAllLeases(Model m){
        m.addAttribute("allLeases", leaseServices.getAllLeases());
        return "admin/leases";
    }
    @GetMapping("/admin/posts")
    public String adminGetAllPosts(Model m){
        m.addAttribute("allPosts", postServices.getAllPosts());
        return "admin/posts";
    }
    @GetMapping("/admin/images")
    public String adminGetAllImages(Model m){
        m.addAttribute("allImages", imageServices.getAllImages());
        return "admin/images";
    }
    @GetMapping("/admin/payments")
    public String adminGetAllPayments(Model m){
        m.addAttribute("allPayments", paymentServices.getAllPayments());
        return "admin/payments";
    }
    @GetMapping("/admin/message-threads")
    public String adminGetAllMessageThreads(Model m){
        m.addAttribute("allMessageThreads", messageThreadServices.getAllMessageThreads());
        return "admin/message-threads";
    }





//    @PostMapping("/admin/all-users")
//    public String adminUpdateUser(@ModelAttribute Member member, Model m){
//        m.addAttribute("selectedUser", member);
//        return "admin";
//    }

}

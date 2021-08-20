package com.cooperweisbach.CommunityGarden.controllers.Admin;

import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class AdminMessageThreadController {

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
    public AdminMessageThreadController(MemberServices memberServices,
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

    @GetMapping("/admin/message-threads")
    public String adminGetAllMessageThreads(Model m){
        m.addAttribute("allMessageThreads", messageThreadServices.getAllMessageThreads());
        return "admin/message-threads/message-threads";
    }
}

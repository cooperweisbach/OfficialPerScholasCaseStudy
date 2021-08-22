package com.cooperweisbach.CommunityGarden.controllers.Admin;

import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class AdminPaymentController {

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
    public AdminPaymentController(MemberServices memberServices,
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

    @GetMapping("/admin/payments")
    public String adminGetAllPayments(Model m){
        m.addAttribute("allPayments", paymentServices.getAllPayments());
        return "admin/payments/payments";
    }

    @PostMapping("/admin/payments")
    public String adminGetAllPaymentsPost(Model m){
        m.addAttribute("allPayments", paymentServices.getAllPayments());
        return "admin/payments/payments";
    }

}

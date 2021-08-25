package com.cooperweisbach.CommunityGarden.controllers.Member;

import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class MemberAccountController {

    private MemberServices memberServices;
    private LeasableServices leasableServices;
    private LeaseServices leaseServices;
    private PostServices postServices;
    private MessageThreadServices messageThreadServices;
    private UserRolesServices userRolesServices;
    private ImageServices imageServices;
    private ConfigurationServices configurationServices;

    @Autowired
    public MemberAccountController(MemberServices memberServices,
                           LeasableServices leasableServices,
                           LeaseServices leaseServices,
                           PostServices postServices,
                           MessageThreadServices messageThreadServices,
                           UserRolesServices userRolesServices,
                           ImageServices imageServices,
                           ConfigurationServices configurationServices) {
        this.memberServices = memberServices;
        this.leasableServices = leasableServices;
        this.leaseServices = leaseServices;
        this.postServices = postServices;
        this.messageThreadServices = messageThreadServices;
        this.userRolesServices = userRolesServices;
        this.imageServices = imageServices;
        this.configurationServices = configurationServices;
    }


    @GetMapping("/members/leasables")
    public String leasables(Model m){
        m.addAttribute("currentConfig", configurationServices.getPublishedConfiguration());
        return "/member/leasables";
    }

    @PostMapping("/members/leasables/rent")
    public String rentLeasables(Model m, @RequestParam("id") Integer id){
        log.warn("The id value returned is: "+ id);
        return "member/rent";
    }
}

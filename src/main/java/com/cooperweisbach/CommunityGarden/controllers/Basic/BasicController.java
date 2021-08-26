package com.cooperweisbach.CommunityGarden.controllers.Basic;


import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
@Slf4j
public class BasicController {

    private MemberServices memberServices;
    private LeasableServices leasableServices;
    private LeaseServices leaseServices;
    private PostServices postServices;
    private MessageThreadServices messageThreadServices;
    private UserRolesServices userRolesServices;
    private ImageServices imageServices;
    private ConfigurationServices configurationServices;

    @Autowired
    public BasicController(MemberServices memberServices,
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

    @GetMapping({"/", "/index"})
    public String home(Model m, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            m.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        long memberCount = memberServices.memberCount();
        log.warn(String.valueOf(memberCount));
        m.addAttribute("totalMembers", memberCount);
        m.addAttribute("typeTreeMap", (Map<String, Long>)leasableServices.getLeasableCountsByType());

//        for(Map.Entry<String, Long>   e: leasableServices.getLeasableCountsByType().entrySet()){
//            log.warn(e.getKey() + " " + e.getValue());
//            m.addAttribute(e.getKey(), e.getValue());
//        }
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/contact-us")
    public String contactUs(){
        return "contactUs";
    }


}

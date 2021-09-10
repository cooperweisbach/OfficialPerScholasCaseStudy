package com.cooperweisbach.CommunityGarden.controllers.Admin;


import com.cooperweisbach.CommunityGarden.models.Configuration;
import com.cooperweisbach.CommunityGarden.models.Leasable;
import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@Slf4j
public class AdminLeasableController {

    private MemberServices memberServices;
    private LeasableServices leasableServices;
    private LeasableStatusServices leasableStatusServices;
    private LeasableTypeServices leasableTypeServices;
    private LeaseServices leaseServices;
    private PostServices postServices;
    private ImageServices imageServices;
    private PaymentServices paymentServices;
    private MessageThreadServices messageThreadServices;
    private ConfigurationServices configurationServices;

    @Autowired
    public AdminLeasableController(MemberServices memberServices,
                                  LeasableServices leasableServices,
                                   LeasableStatusServices leasableStatusServices,
                                   LeasableTypeServices leasableTypeServices,
                                  LeaseServices leaseServices,
                                  PostServices postServices,
                                  ImageServices imageServices,
                                  PaymentServices paymentServices,
                                  MessageThreadServices messageThreadServices,
                                   ConfigurationServices configurationServices) {
        this.memberServices = memberServices;
        this.leasableServices = leasableServices;
        this.leasableStatusServices = leasableStatusServices;
        this.leasableTypeServices = leasableTypeServices;
        this.leaseServices = leaseServices;
        this.postServices = postServices;
        this.imageServices = imageServices;
        this.paymentServices = paymentServices;
        this.messageThreadServices = messageThreadServices;
        this.configurationServices = configurationServices;
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////Admin RequestHandler To Get READ Data//////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    //Gets us each and every leasable
    @GetMapping("/admin/leasables")
    public String adminGetAllLeasables(Model m, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            m.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        m.addAttribute("numberOfLeasables", leasableServices.count());
        m.addAttribute("allLeasables", leasableServices.getAllLeasables());
        m.addAttribute("newConfig", new Configuration());
        m.addAttribute("savedConfigurations", configurationServices.getAllConfigurations());
        m.addAttribute("leaseHistory", leaseServices.getAllLeases());

        m.addAttribute("leasableToAlter", new Leasable());
        m.addAttribute("leasableToCreate", new Leasable());
        m.addAttribute("leasableStatuses", leasableStatusServices.getEveryLeasableStatus());
        m.addAttribute("leasableTypes", leasableTypeServices.getEveryLeasableType());
        return "admin/leasables/leasables";
    }

    @PostMapping("/admin/leasables")
    public String adminGetAllLeasablesPost(Model m, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            m.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        m.addAttribute("allLeasables", leasableServices.getAllLeasables());
        m.addAttribute("newConfig", new Configuration());
        m.addAttribute("savedConfigurations", configurationServices.getAllConfigurations());
        m.addAttribute("leaseHistory", leaseServices.getAllLeases());

        m.addAttribute("leasableToAlter", new Leasable());
        m.addAttribute("leasableToCreate", new Leasable());
        m.addAttribute("leasableStatuses", leasableStatusServices.getEveryLeasableStatus());
        m.addAttribute("leasableTypes", leasableTypeServices.getEveryLeasableType());
        return "admin/leasables/leasables";
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////Admin RequestHandler To Update Data////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////


    //Response to event on the users page
    //Redirect admin to specific page where they're updating the user's info
    //, @RequestParam(name="MemId") Integer memberId
    @PostMapping("/admin/leasables/update/{id}")
    public String updateLeasablePage(@PathVariable(name="id") Integer id, Model model) {
        log.warn("String Id: " + id);
        Leasable leasableToUpdate = leasableServices.getLeasableById(id);
        leasableToUpdate.setLeasableId(id);
        model.addAttribute("leasableToUpdate", leasableToUpdate);
        model.addAttribute("leasableStatuses", leasableStatusServices.getEveryLeasableStatus());
        model.addAttribute("leasableTypes", leasableTypeServices.getEveryLeasableType());
        return "admin/leasables/leasable-update";
    }


    @PostMapping("/admin/leasables/update-approved")
    public ModelAndView redirectToSuccessfulUpdate(HttpServletRequest request, @ModelAttribute("leasableToUpdate") Leasable leasableToUpdate){
        log.warn("Updated leasable Id " + String.valueOf(leasableToUpdate.getLeasableId()));
        Leasable databaseLeasasble = leasableServices.getLeasableById(leasableToUpdate.getLeasableId());
        databaseLeasasble.setLeasableSize(leasableToUpdate.getLeasableSize());
        databaseLeasasble.setLeasableStatus(leasableToUpdate.getLeasableStatus());
        databaseLeasasble.setLeasableType(leasableToUpdate.getLeasableType());
        databaseLeasasble.setLeasableCode(leasableToUpdate.getLeasableCode());
        databaseLeasasble.setLeasableYearlyRent(leasableToUpdate.getLeasableYearlyRent());
        leasableServices.save(databaseLeasasble);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/leasables");
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////Admin RequestHandler To Delete Data////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/admin/leasables/delete/{leasableId}")
    public String deleteLeasablePage(@PathVariable("leasableId") Integer leasableId, Model model){
        log.warn("Attempted to delete user " + leasableId);
        Leasable leasableToDelete = leasableServices.getLeasableById(leasableId);
        model.addAttribute("leasableToDelete", leasableToDelete);
        return "admin/leasables/leasable-delete";
    }


    @PostMapping("/admin/leasables/delete-approved")
    public ModelAndView redirectToSuccessfulDelete(HttpServletRequest request, @ModelAttribute("memberToDelete") Leasable leasableToDelete){
        int leasableId = leasableToDelete.getLeasableId();
        log.warn("Leasable to delete " + leasableId);
        leasableServices.deleteLeasableById(leasableId);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/leasables");
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////Admin RequestHandler To Create Data////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////


//    @PostMapping("/admin/leasables/create")
//    public String createLeasablePage(Model model){
//        model.addAttribute("leasableToCreate", new Leasable());
//        model.addAttribute("leasableStatuses", leasableStatusServices.getEveryLeasableStatus());
//        model.addAttribute("leasableTypes", leasableTypeServices.getEveryLeasableType());
//        return "/admin/leasables/leasable-create";
//    }


    @PostMapping("/admin/leasables/create-approved")
    public ModelAndView redirectToSuccessfulCreate(HttpServletRequest request, @ModelAttribute("memberToCreate") Leasable leasableToCreate){
        leasableServices.save(leasableToCreate);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/leasables");
    }
}

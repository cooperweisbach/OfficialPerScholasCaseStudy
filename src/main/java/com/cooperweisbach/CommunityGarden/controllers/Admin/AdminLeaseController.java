package com.cooperweisbach.CommunityGarden.controllers.Admin;


import com.cooperweisbach.CommunityGarden.models.Leasable;
import com.cooperweisbach.CommunityGarden.models.LeasableStatus;
import com.cooperweisbach.CommunityGarden.models.Lease;
import com.cooperweisbach.CommunityGarden.models.LeaseStatus;
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
public class AdminLeaseController {

    private final String LEASABLE_STATUS_AVAILABLE_FOR_RENT = "open";
    private final String LEASABLE_STATUS_NOT_AVAILABLE = "leased";
    private final String LEASE_STATUS_EXPIRED = "expired";
    private final String LEASE_STATUS_TERMINATED_EARLY = "terminated early";

    private MemberServices memberServices;
    private LeaseServices leaseServices;
    private LeaseStatusServices leaseStatusServices;
    private PostServices postServices;
    private ImageServices imageServices;
    private PaymentServices paymentServices;
    private MessageThreadServices messageThreadServices;
    private LeasableServices leasableServices;
    private LeasableStatusServices leasableStatusServices;

    @Autowired
    public AdminLeaseController(MemberServices memberServices,
                                LeaseServices leaseServices,
                                LeaseStatusServices leaseStatusServices,
                                PostServices postServices,
                                ImageServices imageServices,
                                PaymentServices paymentServices,
                                MessageThreadServices messageThreadServices,
                                LeasableServices leasableServices,
                                LeasableStatusServices leasableStatusServices) {
        this.memberServices = memberServices;
        this.leaseServices = leaseServices;
        this.leaseStatusServices = leaseStatusServices;
        this.postServices = postServices;
        this.imageServices = imageServices;
        this.paymentServices = paymentServices;
        this.messageThreadServices = messageThreadServices;
        this.leasableServices = leasableServices;
        this.leasableStatusServices = leasableStatusServices;
    }

    ////////////////////////////Helper Methods//////////////////////////////////////
    private LeasableStatus openLeasableStatus() {
       return leasableStatusServices.getLeasableStatusByLeasableStatus(LEASABLE_STATUS_AVAILABLE_FOR_RENT);
    }
    private LeasableStatus leasedLeasableStatus() {
        return leasableStatusServices.getLeasableStatusByLeasableStatus(LEASABLE_STATUS_NOT_AVAILABLE);
    }

    private LeaseStatus expiredLeaseStatus() {
        return leaseStatusServices.getByLeaseStatusName(LEASE_STATUS_EXPIRED);
    }

    private LeaseStatus terminatedEarlyLeaseStatus(){
        return leaseStatusServices.getByLeaseStatusName(LEASE_STATUS_TERMINATED_EARLY);
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////Admin RequestHandler To Get READ Data//////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    //Gets us each and every lease
    @GetMapping("/admin/leases")
    public String adminGetAllLeases(Model m, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            m.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        m.addAttribute("allLeases", leaseServices.getAllLeases());

        m.addAttribute("leaseToCreate", new Lease());
        m.addAttribute("leaseStatuses", leaseStatusServices.getEveryLeaseStatus());
        m.addAttribute("allMembers",memberServices.getAllMembers());
        m.addAttribute("availableLeasables",leasableServices.getAllByLeasableStatus(LEASABLE_STATUS_AVAILABLE_FOR_RENT));

        return "admin/leases/leases";
    }

    @PostMapping("/admin/leases")
    public String adminGetAllLeasesPost(Model m, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            m.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        m.addAttribute("allLeases", leaseServices.getAllLeases());
        m.addAttribute("leaseToCreate", new Lease());
        m.addAttribute("leaseStatuses", leaseStatusServices.getEveryLeaseStatus());
        m.addAttribute("allMembers",memberServices.getAllMembers());
        m.addAttribute("availableLeasables",leasableServices.getAllByLeasableStatus(LEASABLE_STATUS_AVAILABLE_FOR_RENT));
        return "admin/leases/leases";
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////Admin RequestHandler To Update Data////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////


    //Response to event on the users page
    //Redirect admin to specific page where they're updating the user's info
    //, @RequestParam(name="MemId") Integer memberId
    @PostMapping("/admin/leases/update/{id}")
    public String updateLeasePage(@PathVariable(name="id") Integer id, Model model) {
        log.warn("String Id: " + id);
        Lease leaseToUpdate = leaseServices.getLeaseById(id);
        leaseToUpdate.setLeaseId(id);
        model.addAttribute("leaseToUpdate", leaseToUpdate);
        model.addAttribute("leaseStatuses", leaseStatusServices.getEveryLeaseStatus());
        model.addAttribute("availableLeasables",leasableServices.getAllByLeasableStatus(LEASABLE_STATUS_AVAILABLE_FOR_RENT));
        model.addAttribute("allMembers",memberServices.getAllMembers());
        return "admin/leases/lease-update";
    }


    @PostMapping("/admin/leases/update-approved")
    public ModelAndView redirectToSuccessfulUpdate(HttpServletRequest request, @ModelAttribute("leaseToUpdate") Lease leaseToUpdate){
        log.warn("Updated lease Id " + String.valueOf(leaseToUpdate.getLeaseId()));
        log.warn("updated Leas leasable code " + leaseToUpdate.getLeasable().getLeasableCode());
        Lease databaseLease = leaseServices.getLeaseById(leaseToUpdate.getLeaseId());
        Leasable oldLeaseLeasable = leasableServices.getLeasableById(databaseLease.getLeasable().getLeasableId());
        oldLeaseLeasable.setLeasableStatus(openLeasableStatus());
        Leasable newLeaseLeasable = leasableServices.getLeasableById(leaseToUpdate.getLeasable().getLeasableId());
        if(leaseToUpdate.getLeaseStatus() == expiredLeaseStatus() || leaseToUpdate.getLeaseStatus() == terminatedEarlyLeaseStatus()){
            newLeaseLeasable.setLeasableStatus(openLeasableStatus());
        } else{
            newLeaseLeasable.setLeasableStatus(leasedLeasableStatus());
        }
        databaseLease.setLeaseStatus(leaseToUpdate.getLeaseStatus());
        databaseLease.setMember(leaseToUpdate.getMember());
        databaseLease.setLeasable(leaseToUpdate.getLeasable());
        leaseServices.save(databaseLease);
        leasableServices.save(oldLeaseLeasable);
        leasableServices.save(newLeaseLeasable);
       // leasableServices.getLeasableById(leaseToUpdate.getLeasable().getLeasableId()).setLeasableStatus(leasableStatusServices.getLeasableStatusByLeasableStatus(LEASABLE_STATUS_AVAILABLE_FOR_RENT));
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/leases");
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////Admin RequestHandler To Delete Data////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/admin/leases/delete/{leaseId}")
    public String deleteLeasePage(@PathVariable("leaseId") Integer leaseId, Model model){
        log.warn("Attempted to delete lease " + leaseId);
        Lease leaseToDelete = leaseServices.getLeaseById(leaseId);
        model.addAttribute("leaseToDelete", leaseToDelete);
        return "admin/leases/lease-delete";
    }


    @PostMapping("/admin/leases/delete-approved")
    public ModelAndView redirectToSuccessfulDelete(HttpServletRequest request, @ModelAttribute("leaseToDelete") Lease leaseToDelete){
        int leaseId = leaseToDelete.getLeaseId();
        log.warn("This should be the lease Id of the lease to delete " + leaseId);
        leaseToDelete = leaseServices.getLeaseById(leaseId);
        Leasable leasableForDeletedLease = leasableServices.getLeasableById(leaseToDelete.getLeasable().getLeasableId());
       log.warn("Leasable code for soon to be deleted lease"+leasableForDeletedLease.getLeasableCode());
        if(leasableForDeletedLease.getLeasableStatus() == leasedLeasableStatus()){
            leasableForDeletedLease.setLeasableStatus(openLeasableStatus());
        }
        log.warn("Lease to delete " + leaseId);
        leaseServices.deleteLeaseById(leaseId);
        leasableServices.save(leasableForDeletedLease);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/leases");
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////Admin RequestHandler To Create Data////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////


    @PostMapping("/admin/leases/create")
    public String createLeasePage(Model model){
        model.addAttribute("leaseToCreate", new Lease());
        model.addAttribute("leaseStatuses", leaseStatusServices.getEveryLeaseStatus());
        model.addAttribute("allMembers",memberServices.getAllMembers());
        model.addAttribute("availableLeasables",leasableServices.getAllByLeasableStatus(LEASABLE_STATUS_AVAILABLE_FOR_RENT));
        return "/admin/leases/lease-create";
    }


    @PostMapping("/admin/leases/create-approved")
    public ModelAndView redirectToSuccessfulCreate(HttpServletRequest request, @ModelAttribute("leaseToCreate") Lease leaseToCreate){
        log.warn("Made tits to redirect method");
        leaseServices.save(leaseToCreate);
        Leasable rentedLeasable = leasableServices.getLeasableById(leaseToCreate.getLeasable().getLeasableId());
        if(leaseToCreate.getLeaseStatus()!=expiredLeaseStatus() || leaseToCreate.getLeaseStatus()!=terminatedEarlyLeaseStatus()){
            rentedLeasable.setLeasableStatus(leasedLeasableStatus());
        }
        leasableServices.save(rentedLeasable);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/leases");
    }
}

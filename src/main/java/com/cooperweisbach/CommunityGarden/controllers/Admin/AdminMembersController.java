package com.cooperweisbach.CommunityGarden.controllers.Admin;

import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Slf4j
public class AdminMembersController {

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
    public AdminMembersController(MemberServices memberServices,
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

    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////Admin RequestHandlers To Get READ Data/////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    //Gets us each and every user
    @GetMapping("/admin/users")
    public String adminGetAllUsersGet(Model m){
//        m.addAttribute("user", new Member());
        m.addAttribute("allMembers", memberServices.getAllMembers());
        m.addAttribute("memberStatuses", memberStatusServices.getEveryMemberStatus());
        m.addAttribute("userRoles", userRolesServices.getEveryUserRole());
        return "admin/members/members";
    }

    @PostMapping("/admin/users")
    public String adminGetAllUsersPost(Model m){
//        m.addAttribute("user", new Member());
        m.addAttribute("allMembers", memberServices.getAllMembers());
        return "admin/members/members";
    }

    @PostMapping("/admin/users/update-approved")
    public ModelAndView redirectToSuccessfulUpdate(HttpServletRequest request, @ModelAttribute("memberToUpdate") Member memberToUpdate){
        log.warn("Updated member Id " + String.valueOf(memberToUpdate.getMemberId()));
        Member databaseMember = memberServices.getMemberById(memberToUpdate.getMemberId());
        databaseMember.setFirstName(memberToUpdate.getFirstName());
        databaseMember.setLastName(memberToUpdate.getLastName());
        databaseMember.setEmail(memberToUpdate.getEmail());
        databaseMember.setUserRoles(memberToUpdate.getUserRoles());
        databaseMember.setMemberStatus(memberToUpdate.getMemberStatus());
        databaseMember.setPassword(memberToUpdate.getPassword());
        databaseMember.setPhoneNumber(memberToUpdate.getPhoneNumber());
        memberServices.save(databaseMember);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/users");
    }


    //Response to event on the users page
    //Redirect admin to specific page where they're updating the user's info
    //, @RequestParam(name="MemId") Integer memberId
    @PostMapping("/admin/users/update/{id}")
    public String updateUserPage(@PathVariable(name="id") Integer id, Model model) {
        log.warn("String Id: " + id);
        Member memberToUpdate = memberServices.getMemberById(id);
        memberToUpdate.setMemberId(id);
        model.addAttribute("memberToUpdate", memberToUpdate);
        model.addAttribute("memberStatuses", memberStatusServices.getEveryMemberStatus());
        model.addAttribute("userRoles", userRolesServices.getEveryUserRole());
        return "admin/members/member-update";
    }

    @PostMapping("/admin/users/create")
    public String createUserPage(Model model){
        model.addAttribute("memberToCreate", new Member());
        model.addAttribute("memberStatuses", memberStatusServices.getEveryMemberStatus());
        model.addAttribute("userRoles", userRolesServices.getEveryUserRole());
        return "/admin/members/member-create";
    }


    @PostMapping("/admin/users/create-approved")
    public ModelAndView redirectToSuccessfulCreate(HttpServletRequest request, @ModelAttribute("memberToCreate") Member memberToCreate){
        memberServices.save(memberToCreate);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/users");
    }


    @PostMapping("/admin/users/delete/{memberId}")
    public String deleteUserPage(@PathVariable("memberId") Integer memberId, Model model){
        log.warn("Attempted to delete user " + memberId);
        Member memberToDelete = memberServices.getMemberById(memberId);
        model.addAttribute("memberToDelete", memberToDelete);
        return "admin/members/member-delete";
    }


    @PostMapping("/admin/users/delete-approved")
    public ModelAndView redirectToSuccessfulDelete(HttpServletRequest request, @ModelAttribute("memberToDelete") Member memberToDelete){
        log.warn("Member to delete " + memberToDelete.getMemberId());
        memberServices.deleteMemberById(memberToDelete.getMemberId());
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/users");
    }

}

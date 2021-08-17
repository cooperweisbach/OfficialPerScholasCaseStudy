package com.cooperweisbach.CommunityGarden.controllers;

import com.cooperweisbach.CommunityGarden.models.Lease;
import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
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

    //Gets us each and every user
    @GetMapping("/admin/users")
    public String adminGetAllUsersGet(Model m){
//        m.addAttribute("user", new Member());
        m.addAttribute("allMembers", memberServices.getAllMembers());
        m.addAttribute("memberStatuses", memberStatusServices.getEveryMemberStatus());
        m.addAttribute("userRoles", userRolesServices.getEveryUserRole());
        return "admin/members";
    }

    @PostMapping("/admin/users")
    public String adminGetAllUsersPost(Model m){
//        m.addAttribute("user", new Member());
        m.addAttribute("allMembers", memberServices.getAllMembers());
        return "admin/members";
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
    @PostMapping("/admin/users/{id}")
    public String updateUserPage(@PathVariable(name="id") String id, Model model) {
        log.warn("String Id: " + id);
        Member memberToUpdate = memberServices.getMemberById(Integer.parseInt(id));
        memberToUpdate.setMemberId(Integer.parseInt(id));
        model.addAttribute("memberToUpdate", memberToUpdate);
        model.addAttribute("memberStatuses", memberStatusServices.getEveryMemberStatus());
        model.addAttribute("userRoles", userRolesServices.getEveryUserRole());
        return "admin/member-update";
    }

    @PostMapping("/admin/users/delete/{memberId}")
    public String deleteUserPage(@PathVariable("memberId") Integer memberId, Model model){
        log.warn("Attempted to delete user " + memberId);
        Member memberToDelete = memberServices.getMemberById(memberId);
        model.addAttribute("memberToDelete", memberToDelete);
        model.addAttribute("allMembers", memberServices.getAllMembers());
//        memberServices.deleteMemberById(id);
        return "admin/member-delete";
    }

    @GetMapping("/admin/users/create")
    public String createUserPage(Model model){
        model.addAttribute("newUser", new Member());
        return "/admin/users/member-create";
    }


//    @PostMapping("/admin/users/member-delete")
//    public String userUpdateValid(@RequestBody Integer memberId, Model model){
//        log.warn("Attempted to delete user " + memberId);
//        model.addAttribute("allMembers", memberServices.getAllMembers());
////        memberServices.deleteMemberById(id);
//        return "/admin/members";
//    }

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

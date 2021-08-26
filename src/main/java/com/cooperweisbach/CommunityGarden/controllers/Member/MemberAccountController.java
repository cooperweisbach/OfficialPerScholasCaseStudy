package com.cooperweisbach.CommunityGarden.controllers.Member;

import com.cooperweisbach.CommunityGarden.exceptions.UserAlreadyExistsException;
import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.security.MemberDTO;
import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

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
    public String leasables(Model m, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            m.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        log.warn("entered mapping config");
        m.addAttribute("currentConfig", configurationServices.getPublishedConfiguration());
        return "/member/leasables";
    }

    @PostMapping("/members/leasables/rent")
    public String rentLeasables(Model m, @RequestParam("id") Integer id, HttpServletRequest request, Model model){
        log.warn("Entered the rent page");
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            model.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        model.addAttribute("leasableToRent",leasableServices.getLeasableById(id));
        log.warn("The id value returned is: "+ id);
        return "member/rent";
    }

    @GetMapping("/members")
    public String memberHomePage(Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            model.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        model.addAttribute("messageThreads", messageThreadServices.getAllMessageThreads());
        return "member/message-threads";
    }

    @GetMapping("/members/member")
    public String memberInfoPage(Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if(principal != null){
            log.warn(principal.getName());
            model.addAttribute("currentUser", memberServices.getMemberByEmail(principal.getName()));
        }
        return "member/member";
    }

    @PostMapping("/members/leasables/rent-confirmed")
    public ModelAndView rentalConfirmed(HttpServletRequest request, @RequestParam("leasableRented")Integer leasableRented ){
        Principal principal = request.getUserPrincipal();
        Member member = memberServices.getMemberByEmail(principal.getName());
        leaseServices.createNewLease(leasableRented, member);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/members/leases");
    }

    @GetMapping("/members/leases")
    public String viewLeases(HttpServletRequest request, Model model){
        Principal principal = request.getUserPrincipal();
        Member member = memberServices.getMemberByEmail(principal.getName());
        model.addAttribute("memberLeases", member.getLeases());
        return "member/leases";
    }

    @GetMapping("/registration")
    public String register(Model model){
        MemberDTO newMember = new MemberDTO();
        model.addAttribute("newMember", newMember);
        return "registration";
    }

    @PostMapping("/registration/processing")
    public ModelAndView processRegistration(@ModelAttribute("newMember") @Valid MemberDTO memberDTO,
                                            HttpServletRequest request, Errors errors){
        log.warn("registration processing");
        log.warn(memberDTO.getFirstName());
        log.warn(memberDTO.getEmail());

        ModelAndView mav = new ModelAndView();
        try{
            Member newMember = memberServices.registerNewMember(memberDTO);
        } catch(UserAlreadyExistsException uaee){
            log.warn("User already exists");
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        }
        mav.setViewName("redirect:/");
//        mav.addObject("newMember", memberDTO);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return mav;
    }

}

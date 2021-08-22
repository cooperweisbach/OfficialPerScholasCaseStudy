package com.cooperweisbach.CommunityGarden.controllers.Admin;

import com.cooperweisbach.CommunityGarden.models.MessageThread;
import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    private MessageThreadStatusServices messageThreadStatusServices;

    @Autowired
    public AdminMessageThreadController(MemberServices memberServices,
                               LeasableServices leasableServices,
                               MemberStatusServices memberStatusServices,
                               UserRolesServices userRolesServices,
                               LeaseServices leaseServices,
                               PostServices postServices,
                               ImageServices imageServices,
                               PaymentServices paymentServices,
                               MessageThreadServices messageThreadServices,
                                        MessageThreadStatusServices messageThreadStatusServices) {
        this.memberServices = memberServices;
        this.leasableServices = leasableServices;
        this.memberStatusServices = memberStatusServices;
        this.userRolesServices = userRolesServices;
        this.leaseServices = leaseServices;
        this.postServices = postServices;
        this.imageServices = imageServices;
        this.paymentServices = paymentServices;
        this.messageThreadServices = messageThreadServices;
        this.messageThreadStatusServices = messageThreadStatusServices;
    }

    @GetMapping("/admin/message-threads")
    public String adminGetAllMessageThreads(Model m){
        m.addAttribute("allMessageThreads", messageThreadServices.getAllMessageThreads());
        return "admin/message-threads/message-threads";
    }

    @PostMapping("/admin/message-threads")
    public String adminGetAllMessageThreadsPost(Model m){
        m.addAttribute("allMessageThreads", messageThreadServices.getAllMessageThreads());
        return "admin/message-threads/message-threads";
    }

    @PostMapping("/admin/message-threads/create")
    public String adminCreateMessageThreads(Model m){
        m.addAttribute("messageThreadToCreate", new MessageThread());
        m.addAttribute("messageThreadStatuses", messageThreadStatusServices.getEveryMessageThreadStatus());
        return "/admin/message-threads/message-thread-create";
    }

    @PostMapping("/admin/message-threads/create-approved")
    public ModelAndView adminCreateApproved(HttpServletRequest request,
                                            @ModelAttribute("messageThreadToCreate") MessageThread messageThreadToCreate){
        messageThreadServices.save(messageThreadToCreate);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/message-threads");
    }

    @PostMapping("/admin/message-threads/delete/{id}")
    public String adminDeleteMessageThread(@PathVariable("id")Integer id, Model model){
        MessageThread messageThreadToDelete = messageThreadServices.getMessageThreadById(id);
        model.addAttribute("messageThreadToDelete", messageThreadToDelete);
        return "/admin/message-threads/message-thread-delete";
    }

    @PostMapping("/admin/message-threads/delete-approved")
    public ModelAndView adminDeleteApproved(HttpServletRequest request,
                                            @ModelAttribute("messageThreadToCreate") MessageThread messageThreadToDelete){
        messageThreadServices.deleteByID(messageThreadToDelete.getMessageThreadId());
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/message-threads");
    }

    @PostMapping("/admin/message-threads/update/{id}")
    public String adminUpdateMessageThread(@PathVariable("id")Integer id, Model model){
        MessageThread messageThreadToUpdate = messageThreadServices.getMessageThreadById(id);
        model.addAttribute("messageThreadToUpdate", messageThreadToUpdate);
        model.addAttribute("messageThreadStatuses", messageThreadStatusServices.getEveryMessageThreadStatus());
        return "/admin/message-threads/message-thread-update";
    }

    @PostMapping("/admin/message-threads/update-approved")
    public ModelAndView adminUpdateMessageThreadApproved(HttpServletRequest request,
                                                         @ModelAttribute("messageThreadToUpdate") MessageThread messageThreadToUpdate) {
        MessageThread databaseMessageThread = messageThreadServices.getMessageThreadById(messageThreadToUpdate.getMessageThreadId());
        databaseMessageThread.setMessageThreadName(messageThreadToUpdate.getMessageThreadName());
        databaseMessageThread.setMessageThreadStatus(messageThreadToUpdate.getMessageThreadStatus());
        messageThreadServices.save(databaseMessageThread);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/message-threads");
    }

}

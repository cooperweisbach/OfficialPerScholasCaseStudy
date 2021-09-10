package com.cooperweisbach.CommunityGarden.controllers.Admin;

import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.models.Post;
import com.cooperweisbach.CommunityGarden.models.PostTag;
import com.cooperweisbach.CommunityGarden.models.PostTagContainer;
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
import java.util.List;

@Controller
@Slf4j
public class AdminPostController {

    private MemberServices memberServices;
    private LeasableServices leasableServices;
    private MemberStatusServices memberStatusServices;
    private UserRolesServices userRolesServices;
    private LeaseServices leaseServices;
    private PostServices postServices;
    private ImageServices imageServices;
    private PaymentServices paymentServices;
    private MessageThreadServices messageThreadServices;
    private PostTagServices postTagServices;
    private PostStatusServices postStatusServices;

    @Autowired
    public AdminPostController(MemberServices memberServices,
                                  LeasableServices leasableServices,
                                  MemberStatusServices memberStatusServices,
                                  UserRolesServices userRolesServices,
                                  LeaseServices leaseServices,
                                  PostServices postServices,
                                  ImageServices imageServices,
                                  PaymentServices paymentServices,
                                  MessageThreadServices messageThreadServices,
                                  PostTagServices postTagServices,
                                  PostStatusServices postStatusServices) {
        this.memberServices = memberServices;
        this.leasableServices = leasableServices;
        this.memberStatusServices = memberStatusServices;
        this.userRolesServices = userRolesServices;
        this.leaseServices = leaseServices;
        this.postServices = postServices;
        this.imageServices = imageServices;
        this.paymentServices = paymentServices;
        this.messageThreadServices = messageThreadServices;
        this.postTagServices = postTagServices;
        this.postStatusServices = postStatusServices;
    }


    @GetMapping("/admin/posts")
    public String adminGetAllPostsGet(Model m, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Member currentUser = memberServices.getMemberByEmail(principal.getName());
        if(principal != null){
            log.warn(principal.getName());
            m.addAttribute("currentUser", currentUser);
        }
        m.addAttribute("allPosts", postServices.getAllPosts());
        Post postToCreate = new Post();
        postToCreate.setMember(currentUser);
//        m.addAttribute("postToAlter", postToAlter);
        m.addAttribute("postToCreate", postToCreate);
        m.addAttribute("newTagsStringList", new PostTagContainer());
        m.addAttribute("postStatuses", postStatusServices.findAll());
        return "admin/posts/posts";
    }

    @PostMapping("/admin/posts")
    public String adminGetAllPostsPost(Model m, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Member currentUser = memberServices.getMemberByEmail(principal.getName());
        if(principal != null){
            log.warn(principal.getName());
            m.addAttribute("currentUser", currentUser);
        }
        m.addAttribute("allPosts", postServices.getAllPosts());
        Post postToCreate = new Post();
        postToCreate.setMember(currentUser);
        m.addAttribute("postToCreate", postToCreate);
        m.addAttribute("newTagsStringList", new PostTagContainer());
        m.addAttribute("postStatuses", postStatusServices.findAll());
        return "admin/posts/posts";
    }



    //Response to event on the posts page
    //Redirect admin to specific page where they're updating the user's info
    //, @RequestParam(name="MemId") Integer postId
    @PostMapping("/admin/posts/update/{id}")
    public String updatePostPage(@PathVariable(name="id") Integer id, Model model) {
        log.warn("Post Id: " + id);
        Post postToUpdate = postServices.getPostById(id);
        postToUpdate.setPostId(id);
        model.addAttribute("postToUpdate", postToUpdate);
        model.addAttribute("postStatuses", postStatusServices.findAll());
        model.addAttribute("newTagsStringList", new PostTagContainer());
        model.addAttribute("allMembers", memberServices.getAllMembers());
        model.addAttribute("allTags", postTagServices.findAll());
        return "admin/posts/post-update";
    }
    @PostMapping("/admin/posts/update-approved")
    public ModelAndView redirectToSuccessfulUpdate(HttpServletRequest request,
                                                   @ModelAttribute("postToUpdate") Post postToUpdate,
                                                   @ModelAttribute("newTagsStringList")  PostTagContainer newTagsStringList){
        Post newPost = postServices.getPostById(postToUpdate.getPostId());
        Post emptyPost = postTagServices.savePostTagsFromList(newTagsStringList.getNewPostTagTitles());
        if(postToUpdate.getPostTagList() == null)
            newPost.setPostTagList(emptyPost.getPostTagList());
        else {
            postToUpdate.getPostTagList().addAll(emptyPost.getPostTagList());
            log.warn("Merged lists: " + postToUpdate.getPostTagList());
            newPost.setPostTagList(postToUpdate.getPostTagList());
        }
        newPost.setMember(postToUpdate.getMember());
        newPost.setPostContent(postToUpdate.getPostContent());
        newPost.setPostTitle(postToUpdate.getPostTitle());
        newPost.setPostStatus(postToUpdate.getPostStatus());
        postServices.save(newPost);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/posts");
    }

    @PostMapping("/admin/posts/create")
    public String createUserPage(Model model,HttpServletRequest request){

        Member currentUser = memberServices.getMemberByEmail(request.getUserPrincipal().getName());
        if(currentUser != null){
            model.addAttribute("currentUser", currentUser);
        }
        Post postToCreate = new Post();
        postToCreate.setMember(currentUser);
        model.addAttribute("postToCreate", postToCreate);
        model.addAttribute("newTagsStringList", new PostTagContainer());
        model.addAttribute("postStatuses", postStatusServices.findAll());
//        model.addAttribute("allTags", postTagServices.findAll());
        return "/admin/posts/post-create";
    }


    @PostMapping("/admin/posts/create-approved")
    public ModelAndView redirectToSuccessfulCreate(HttpServletRequest request,
                                                   @ModelAttribute("postToCreate") Post postToCreate,
                                                   @ModelAttribute("newTagsStringList")  PostTagContainer newTagsStringList){

        Post newPost = postTagServices.savePostTagsFromList(newTagsStringList.getNewPostTagTitles());
//        for(PostTag pt: newPost.getPostTagList())
//        if(postToCreate.getPostTagList() == null)
        postToCreate.setPostTagList(newPost.getPostTagList());
        postToCreate.setMember(memberServices.getMemberByEmail(request.getUserPrincipal().getName()));
        postServices.save(postToCreate);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/posts");
    }


    @PostMapping("/admin/posts/delete/{postId}")
    public String deleteUserPage(@PathVariable("postId") Integer postId, Model model){
        log.warn("Attempted to delete user " + postId);
        Post postToDelete = postServices.getPostById(postId);
        model.addAttribute("postToDelete", postToDelete);
        return "admin/posts/post-delete";
    }


    @PostMapping("/admin/posts/delete-approved")
    public ModelAndView redirectToSuccessfulDelete(HttpServletRequest request, @ModelAttribute("postToDelete") Post postToDelete){
        log.warn("Post to delete " + postToDelete.getPostId());
        postServices.deletePostById(postToDelete.getPostId());
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.FOUND);
        return new ModelAndView("redirect:/admin/posts");
    }

}

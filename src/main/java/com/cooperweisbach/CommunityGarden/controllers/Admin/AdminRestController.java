package com.cooperweisbach.CommunityGarden.controllers.Admin;


import com.cooperweisbach.CommunityGarden.exceptions.UserRoleNotFoundException;
import com.cooperweisbach.CommunityGarden.models.*;
import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@org.springframework.web.bind.annotation.RestController
@Slf4j
public class AdminRestController {

    private MemberServices memberServices;
    private UserRolesServices userRolesServices;
    private LeasableServices leasableServices;
    private LeaseServices leaseServices;
    private PostTagServices postTagServices;
    private PostServices postServices;

    @Autowired
    public AdminRestController(MemberServices memberServices,
                               UserRolesServices userRolesServices,
                               LeasableServices leasableServices,
                               LeaseServices leaseServices,
                               PostTagServices postTagServices,
                               PostServices postServices) {
        this.memberServices = memberServices;
        this.userRolesServices = userRolesServices;
        this.leasableServices = leasableServices;
        this.leaseServices = leaseServices;
        this.postTagServices = postTagServices;
        this.postServices = postServices;
    }

    @GetMapping("/api/users/{userRole}")
    public List<Member> getUsers(@PathVariable(name ="userRole")String userRoleName) throws UserRoleNotFoundException {
            List<Member> listMembers = new ArrayList<>();

            try {
                UserRoles userRole = userRolesServices.getUserRole(userRoleName);
                List<Member> memberList = memberServices.getMembersByUserRole(userRole);
                for(Member m: memberList){
                    listMembers.add(m);
                }
            } catch(Exception e){
                e.printStackTrace();
                throw new UserRoleNotFoundException();
            }
            return listMembers;
    }

    @PostMapping("/api/users/check-email-id")
    public Member checkUniqueEmailId(@Param("id") Integer id, @Param("email") String email){
        log.warn("Integer for ID:" + id);
    log.warn("String for Email" + email);
        String[] myEmails = email.split(",");
        email = myEmails[myEmails.length-1];
        log.warn(email);
        return memberServices.checkUniqueEmailId(email, id);
    };

    @PostMapping("/api/users/check-email")
    public Member checkUniqueEmail(@Param("email") String email){
        log.warn("String for Email" + email);
        String[] myEmails = email.split(",");
        email = myEmails[myEmails.length-1];
        log.warn(email);
        return memberServices.checkUniqueEmail(email);
    };

    @PostMapping("/api/leasables/check-code-id")
    public Leasable checkUniqueCodeId(@Param("id") Integer id, @Param("code") String code){
        log.warn("Integer for ID:" + id);
        log.warn("String for code: " + code);
        String[] myCodes = code.split(",");
        code = myCodes[myCodes.length-1];
        log.warn(code);
        return leasableServices.checkUniqueCodeId(id, code);
    };

    @PostMapping("/api/leasables/check-code")
    public Leasable checkUniqueCode(@Param("code") String code){
        log.warn("String for code " + code);
        String[] myCodes = code.split(",");
        code = myCodes[myCodes.length-1];
        log.warn(code);
        return leasableServices.checkUniqueCode(code);
    };


    @PostMapping("/api/leases/check-leasable-id")
    public Lease checkUniqueLeasableId(@Param("id") Integer id, @Param("leasable") String leasable){
        log.warn("Integer for ID:" + id);
        log.warn("String for code: " + leasable);
        String[] myLeasable = leasable.split(",");
        leasable = myLeasable[myLeasable.length-1];
        log.warn(leasable);
        return leaseServices.checkUniqueLeasableId(id, leasable);
//        return new Lease();
    };

    @PostMapping("/api/leases/check-leasable")
    public Lease checkUniqueLeasable(@Param("leasable") String leasable){
        log.warn("String for code " + leasable);
        String[] myLeasable = leasable.split(",");
        leasable = myLeasable[myLeasable.length-1];
        log.warn(leasable);
        return leaseServices.checkUniqueLeasable(leasable);
    };

    @PostMapping("/api/posts/check-title")
    public Post checkUniquePostTitle(@Param("postTitle") String postTitle){
        log.warn("String for title " + postTitle);
//        String[] myTitles = postTitle.split(",");
//        postTitle = myTitles[myTitles.length-1];
        log.warn(postTitle);
        return postServices.checkUniquePostTitle(postTitle);
    };

    @PostMapping("/api/posts/check-title-id")
    public Post checkUniquePostTitleId(@Param("id") Integer id, @Param("postTitle") String postTitle){
        log.warn("String for title " + postTitle);
        log.warn("Int for id " + id);
        log.warn(postTitle);
        return postServices.checkUniquePostTitleId(id, postTitle);
    };


    @PostMapping("/api/posts/add-new-tags")
    public Post addNewPostTags(@Param("postTags") String[] postTags){
        log.warn("Reached post tags");
        log.warn(String.valueOf(postTags.length));
        return postTagServices.savePostTagsFromList(postTags);
    }

    public String notCurrentUser() {return null;};
}
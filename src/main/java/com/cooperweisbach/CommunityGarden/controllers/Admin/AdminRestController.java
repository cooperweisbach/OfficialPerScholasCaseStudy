package com.cooperweisbach.CommunityGarden.controllers.Admin;


import com.cooperweisbach.CommunityGarden.exceptions.UserRoleNotFoundException;
import com.cooperweisbach.CommunityGarden.models.*;
import com.cooperweisbach.CommunityGarden.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private MessageThreadServices messageThreadServices;
    private ConfigurationServices configurationServices;
    private ImageServices imageServices;

    @Autowired
    public AdminRestController(MemberServices memberServices,
                               UserRolesServices userRolesServices,
                               LeasableServices leasableServices,
                               LeaseServices leaseServices,
                               PostTagServices postTagServices,
                               PostServices postServices,
                               MessageThreadServices messageThreadServices,
                               ConfigurationServices configurationServices,
                               ImageServices imageServices) {
        this.memberServices = memberServices;
        this.userRolesServices = userRolesServices;
        this.leasableServices = leasableServices;
        this.leaseServices = leaseServices;
        this.postTagServices = postTagServices;
        this.postServices = postServices;
        this.messageThreadServices = messageThreadServices;
        this.configurationServices = configurationServices;
        this.imageServices = imageServices;
    }

    @PostMapping("/api/users/get-page")
    public Page<Member> getMembersOnPage(@Param("pageNum") Integer pageNum, @Param("numOfResults") Integer numOfResults){
        log.warn("controller");
        log.warn(pageNum.toString());
        log.warn(numOfResults.toString());
        return memberServices.getMembersInRange(pageNum, numOfResults);
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
    }

    @GetMapping("/api/users/get-all")
    public List<Member> getAllMembers(){return memberServices.getAllMembers();}

    @PostMapping("/api/leasables/get-info-by-id")
    public Leasable getSpecifiedLeasable(@Param("id") Integer id){
        log.warn("looking for leasable: "+ id);
        Leasable returned = leasableServices.getLeasableById(id);
        log.warn(returned.toString());
        return returned;
    }

    @GetMapping("/api/leasables/count")
    public long getLeasablesCount(){return leasableServices.count();}

    @PostMapping("/api/leasables/get-page")
    public Page<Leasable> getLeasablesOnPage(@Param("pageNum") Integer pageNum, @Param("numOfResults") Integer numOfResults){
        return leasableServices.getLeasablesInRange(pageNum, numOfResults);
    }

    @PostMapping("/api/leasables/get-first-page")
    public Page<Leasable> getFirstLeasablePage(@Param("numberOfResults") Integer numberOfResults){
        return leasableServices.getLeasablesInRange(0, numberOfResults);
    }

    @GetMapping("/api/leasables/get-all")
    public List<Leasable> getAllLeasables(){
        return leasableServices.getAllLeasables();
    }

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

    @PostMapping("/api/leases/member-id")
    public List<Lease> getLeasesByMemberId(@Param("memberId") Integer memberId){
        return leaseServices.findLeasesByMemberId(memberId);
    }

    @PostMapping("/api/leases/leasable-id")
    public List<Lease> getLeasesByLeasableId(@Param("leasableId") Integer leasableId){
        return leaseServices.findLeasesByLeasableId(leasableId);
    }

    @PostMapping("/api/leases/get-page")
    public Page<Lease> getLeasesByPage(@Param("pageNum") Integer pageNum, @Param("numOfResults") Integer numOfResults){
        Page<Lease> page = leaseServices.findLeasesInRange(pageNum, numOfResults);
        log.warn("pageeeee");
        log.warn(page.toString());
        return page;
    }

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


    @PostMapping("/api/posts/get-page")
    public Page<Post> getPostsByPage(@Param("pageNum") Integer pageNum, @Param("numOfResults") Integer numOfResults){
        return postServices.getPostsInRange(pageNum, numOfResults);
    }

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
    public Post addNewPostTags(@Param("postTags") ArrayList<String> postTags){
        log.warn("Reached post tags");
        log.warn(String.valueOf(postTags.size()));
        return postTagServices.savePostTagsFromList(postTags);
    }


    @PostMapping("/api/message-threads/get-page")
    public Page<MessageThread> getMessageThreadByPage(@Param("pageNum") Integer pageNum, @Param("numOfResults") Integer numOfResults){
        return messageThreadServices.getMessageThreadsInRange(pageNum, numOfResults);
    }


    @PostMapping("/api/message-threads/check-thread-name")
    public MessageThread uniqueThreadName(@Param("messageThreadName") String messageThreadName){
        return messageThreadServices.checkUnqiueThreadName(messageThreadName);
    }

    @PostMapping("/api/message-threads/check-name-id")
    public MessageThread uniqueThreadNameId(@Param("id") Integer id, @Param("messageThreadName") String messageThreadName){
        return messageThreadServices.checkUnqiueThreadNameId(id, messageThreadName);
    }


    @PostMapping("/api/configurations/save")
    public Configuration saveConfiguration(@Param("name") String name,
                                           @Param("publish") Boolean publish,
                                           @Param("json") String json){
        log.warn("This is the name value: " + name);
        log.warn("This is the publish value: " + publish);
        log.warn("This is the json value: " + json);
        return configurationServices.save(publish, name, json);
    }

    @GetMapping("/api/configurations/load-all")
    public List<Configuration> loadConfigurations(){
        return configurationServices.getAllConfigurations();
    }

    public String notCurrentUser() {return null;};

    @PostMapping("/api/images/get-page")
    public Page<Image> getImagesByPage(@Param("pageNum") Integer pageNum, @Param("numOfResults") Integer numOfResults){
        return imageServices.getResultsInRange(pageNum, numOfResults);
    }
}

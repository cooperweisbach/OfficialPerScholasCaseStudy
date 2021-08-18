package com.cooperweisbach.CommunityGarden.controllers.Admin;


import com.cooperweisbach.CommunityGarden.exceptions.UserRoleNotFoundException;
import com.cooperweisbach.CommunityGarden.models.Leasable;
import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.models.UserRoles;
import com.cooperweisbach.CommunityGarden.services.LeasableServices;
import com.cooperweisbach.CommunityGarden.services.MemberServices;
import com.cooperweisbach.CommunityGarden.services.UserRolesServices;
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
public class RestController {

    private MemberServices memberServices;
    private UserRolesServices userRolesServices;
    private LeasableServices leasableServices;

    @Autowired
    public RestController(MemberServices memberServices, UserRolesServices userRolesServices, LeasableServices leasableServices) {
        this.memberServices = memberServices;
        this.userRolesServices = userRolesServices;
        this.leasableServices = leasableServices;
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

    @PostMapping("/api/users/check-code-id")
    public Leasable checkUniqueCodeId(@Param("id") Integer id, @Param("code") String code){
        log.warn("Integer for ID:" + id);
        log.warn("String for code: " + code);
        String[] myCodes = code.split(",");
        code = myCodes[myCodes.length-1];
        log.warn(code);
        return leasableServices.checkUniqueCodeId(id, code);
    };

    @PostMapping("/api/users/check-code")
    public Leasable checkUniqueCode(@Param("code") String code){
        log.warn("String for code " + code);
        String[] myCodes = code.split(",");
        code = myCodes[myCodes.length-1];
        log.warn(code);
        return leasableServices.checkUniqueCode(code);
    };



    public String notCurrentUser() {return null;};


}

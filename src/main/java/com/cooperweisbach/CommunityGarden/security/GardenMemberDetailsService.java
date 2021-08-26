package com.cooperweisbach.CommunityGarden.security;

import com.cooperweisbach.CommunityGarden.daos.iMemberRepo;
import com.cooperweisbach.CommunityGarden.daos.iUserRolesRepo;
import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.models.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GardenMemberDetailsService implements UserDetailsService {

    private iMemberRepo memberRepo;
    private iUserRolesRepo userRolesRepo;

    @Autowired
    public GardenMemberDetailsService(iMemberRepo memberRepo, iUserRolesRepo userRolesRepo) {
        this.memberRepo = memberRepo;
        this.userRolesRepo = userRolesRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Member member = memberRepo.getMemberByEmail(s);
        if(member == null){
            throw new UsernameNotFoundException("Cannot find UserName: " + s);
        }
        List<UserRoles> userRoles = member.getUserRoles();
        return new GardenMemberPrincipal(member, userRoles);
    }
}

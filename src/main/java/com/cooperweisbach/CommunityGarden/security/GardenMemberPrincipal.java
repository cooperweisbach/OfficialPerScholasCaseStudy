package com.cooperweisbach.CommunityGarden.security;

import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.models.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

public class GardenMemberPrincipal implements UserDetails {


    private Member member;
    private List<UserRoles> userRoles;

    @Autowired
    public GardenMemberPrincipal(Member member, List<UserRoles> userRoles) {
        this.member = member;
        this.userRoles = userRoles;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(userRoles.isEmpty()){
            return Collections.emptySet();
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        userRoles.forEach(userRole -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getUserRoleName()));
        });
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.member.getPassword();
    }

    @Override
    public String getUsername() {
        return this.member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

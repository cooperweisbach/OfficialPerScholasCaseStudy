//package com.cooperweisbach.CommunityGarden.security;
//
//
//import com.cooperweisbach.CommunityGarden.daos.iMemberRepo;
//import com.cooperweisbach.CommunityGarden.models.Member;
//import com.cooperweisbach.CommunityGarden.services.MemberServices;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//public class MyMemberDetailsService implements UserDetailsService {
//    @Autowired
//    private iMemberRepo memberRepo;
//    private MemberServices memberServices;
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        Member newMember = memberServices.getMemberByEmail(s);
//        if (newMember == null) {
//            throw new UsernameNotFoundException("No user found with username: " + s);
//        }
//
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;
//
//        return new org.springframework.security.core.userdetails.User(
//                newMember.getEmail(), newMember.getPassword().toLowerCase(), enabled, accountNonExpired,
//                credentialsNonExpired, accountNonLocked,
//                getAuthorities(newMember.getUserRoles().stream().map(userRole -> userRole.getUserRoleName()).collect(Collectors.toList())));
//    }
//
//    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role));
//        }
//        return authorities;
//    }
//}

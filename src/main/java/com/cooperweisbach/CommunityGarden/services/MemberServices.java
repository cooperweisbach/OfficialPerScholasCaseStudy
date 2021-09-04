package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iMemberRepo;
import com.cooperweisbach.CommunityGarden.daos.iMemberStatusRepo;
import com.cooperweisbach.CommunityGarden.exceptions.UserAlreadyExistsException;
import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.models.UserRoles;
import com.cooperweisbach.CommunityGarden.security.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class MemberServices {

    private iMemberRepo memberRepo;
    private UserRolesServices userRolesServices;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServices(iMemberRepo memberRepo, UserRolesServices userRolesServices, PasswordEncoder passwordEncoder) {

        this.memberRepo = memberRepo;
        this.userRolesServices = userRolesServices;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Member> getMembersByUserRole(UserRoles userRole){
        return memberRepo.getMemberByUserRoles(userRole);
    };

    public List<Member> getAllMembers(){
        return memberRepo.findAll();
    }

    public long memberCount(){
        return memberRepo.count();
    }

    public Member getMemberById(Integer memberId){ return memberRepo.getById(memberId); };

    public void deleteMemberById(int id) {memberRepo.deleteByMemberId(id);};

    public void save(Member member) {memberRepo.save(member);};

    public Member checkUniqueEmail(String email){
        Member byEmail = memberRepo.getMemberByEmail(email);
        if(byEmail == null){
            return new Member();
        } else {
            return byEmail;
        }
    }

    public Member checkUniqueEmailId(String email, int id){
        Member byId = memberRepo.getByMemberId(id);
        Member byEmail = memberRepo.getMemberByEmail(email);
        if(byEmail == null){
            return byId;
        }
        else if(byId != byEmail){
            return byEmail;
        }
        else {
            return byId;
        }
    }

    public Member getMemberByEmail(String email){
        return memberRepo.getMemberByEmail(email);
    }


    public Member registerNewMember(MemberDTO newMember) throws UserAlreadyExistsException{
        log.warn("reached registration method");
        if(checkIfEmailExists(newMember.getEmail())){
            throw new UserAlreadyExistsException("There is already a user with the email " + newMember.getEmail());
        }

        List<UserRoles> userRoles = userRolesServices.getUserRolesAsListFromUserRoles("ROLE_MEMBER");
        Member persistMember = new Member();
        persistMember.setPassword(passwordEncoder.encode(newMember.getPassword()));
        persistMember.setFirstName(newMember.getFirstName());
        persistMember.setLastName(newMember.getLastName());
        persistMember.setEmail(newMember.getEmail());
        persistMember.setPhoneNumber(newMember.getPhoneNumber());
        persistMember.setUserRoles(userRoles);

        log.warn("just before save operation");
        return memberRepo.save(persistMember);
    }

    private boolean checkIfEmailExists(String email){
        return memberRepo.getMemberByEmail(email) != null;
    }

    public Page<Member> getMembersInRange(int pageNum, int resultSize) {
        log.warn("get members in range");
        log.warn(String.valueOf(pageNum));
        log.warn(String.valueOf(resultSize));
        return memberRepo.findAll(PageRequest.of(pageNum, resultSize));
    }
}

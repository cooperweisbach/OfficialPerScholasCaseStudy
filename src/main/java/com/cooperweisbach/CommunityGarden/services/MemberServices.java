package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iMemberRepo;
import com.cooperweisbach.CommunityGarden.daos.iMemberStatusRepo;
import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.models.UserRoles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class MemberServices {

    private iMemberRepo memberRepo;


    @Autowired
    public MemberServices(iMemberRepo memberRepo) {
        this.memberRepo = memberRepo;
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


}

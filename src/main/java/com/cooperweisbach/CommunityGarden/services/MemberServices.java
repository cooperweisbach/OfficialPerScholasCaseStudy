package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iMemberRepo;
import com.cooperweisbach.CommunityGarden.models.Member;
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

    public List<Member> getAllMembers(){
        return memberRepo.getAll();
    }


}

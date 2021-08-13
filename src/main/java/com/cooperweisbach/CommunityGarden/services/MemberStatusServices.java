package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iMemberStatusRepo;
import com.cooperweisbach.CommunityGarden.models.MemberStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class MemberStatusServices {

    private iMemberStatusRepo memberStatusRepo;

    @Autowired
    public MemberStatusServices(iMemberStatusRepo memberStatusRepo) {
        this.memberStatusRepo = memberStatusRepo;
    }

    public List<MemberStatus> getEveryMemberStatus() {
        return memberStatusRepo.findAll();
    }
}

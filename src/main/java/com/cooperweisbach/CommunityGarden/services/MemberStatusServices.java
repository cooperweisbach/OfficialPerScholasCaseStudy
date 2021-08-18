package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iMemberStatusRepo;
import com.cooperweisbach.CommunityGarden.models.MemberStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class MemberStatusServices {

    private iMemberStatusRepo memberStatusRepo;

    @Autowired
    public MemberStatusServices(iMemberStatusRepo memberStatusRepo) {
        this.memberStatusRepo = memberStatusRepo;
    }

    public void createNewMemberStatus(String newStatus){memberStatusRepo.save(new MemberStatus(newStatus));}

    public List<MemberStatus> getMemberStatusByName(String memberStatus){return memberStatusRepo.findMemberStatusByMemberStatus(memberStatus);}

//    public Optional<MemberStatus> changeMemberStatusName(int id, String newStatusName){memberStatusRepo.findById(id).stream().map(memberStatus -> memberStatus.setMemberStatus(newStatusName))}

    public List<MemberStatus> getEveryMemberStatus() {
        return memberStatusRepo.findAll();
    }
}

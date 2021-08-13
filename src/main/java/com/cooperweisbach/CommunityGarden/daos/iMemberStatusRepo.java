package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface iMemberStatusRepo extends JpaRepository<MemberStatus, Integer> {
    @Override
    List<MemberStatus> findAll();
    void deleteByMemberStatusId(Integer integer);
}

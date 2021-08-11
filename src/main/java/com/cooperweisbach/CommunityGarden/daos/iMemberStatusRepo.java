package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iMemberStatusRepo extends JpaRepository<MemberStatus, Integer> {
    void deleteByMemberStatusId(Integer integer);
}

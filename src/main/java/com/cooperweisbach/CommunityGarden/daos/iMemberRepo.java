package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.Member;
import org.hibernate.internal.util.type.PrimitiveWrapperHelper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface iMemberRepo extends JpaRepository<Member, Integer> {
    Member getByMemberId(int memberId);
    List<Member> findAll();
    long count();
    List<Member> getMemberByUserRoles_UserRoleName(String userRoleName);

    void deleteByMemberId(Integer integer);
}

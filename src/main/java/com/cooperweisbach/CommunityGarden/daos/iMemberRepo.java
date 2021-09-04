package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.Member;
import com.cooperweisbach.CommunityGarden.models.UserRoles;
import org.hibernate.internal.util.type.PrimitiveWrapperHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface iMemberRepo extends JpaRepository<Member, Integer> {
    Member getByMemberId(int memberId);
    List<Member> findAll();
    long count();
    List<Member> getMemberByUserRoles_UserRoleName(String userRoleName);
    List<Member> getMemberByUserRoles(UserRoles userRoles);
    void deleteByMemberId(Integer integer);
    Member getMemberByEmail(String email);
    Page<Member> findAll(Pageable page);
}

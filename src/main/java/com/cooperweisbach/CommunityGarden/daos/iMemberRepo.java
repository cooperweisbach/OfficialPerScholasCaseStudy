package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.Member;
import org.hibernate.internal.util.type.PrimitiveWrapperHelper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface iMemberRepo extends JpaRepository<Member, Integer> {
    List<Member> getAll(); //Get all the members
    Member getByMemberId(int memberId);
    long count();
}
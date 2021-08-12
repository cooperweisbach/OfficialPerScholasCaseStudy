package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface iUserRolesRepo extends JpaRepository<UserRoles, Integer> {
    void deleteUserRolesByUserRoleName(String userRole);
    List<UserRoles> getUserRolesByUserRoleName(String userRoleName);

}

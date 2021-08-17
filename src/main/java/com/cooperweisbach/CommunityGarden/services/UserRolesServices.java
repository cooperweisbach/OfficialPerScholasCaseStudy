package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iUserRolesRepo;
import com.cooperweisbach.CommunityGarden.models.UserRoles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class UserRolesServices {

    private iUserRolesRepo userRolesRepo;

    @Autowired
    public UserRolesServices(iUserRolesRepo userRolesRepo) {
        this.userRolesRepo = userRolesRepo;
    }
    public UserRoles getUserRole(String userRole) {
        return userRolesRepo.getUserRolesByUserRoleName(userRole).get(0);
    }
    public List<UserRoles> getEveryUserRole() {
        return userRolesRepo.findAll();
    }
}

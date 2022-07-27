package com.andresmastracchio.yoprogramo.usecase.impl;

import com.andresmastracchio.yoprogramo.adapter.repository.RoleRepository;
import com.andresmastracchio.yoprogramo.entity.Role;
import com.andresmastracchio.yoprogramo.entity.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> getByRoleName(RoleName roleName){
        return roleRepository.findByRoleName(roleName);
    }
}

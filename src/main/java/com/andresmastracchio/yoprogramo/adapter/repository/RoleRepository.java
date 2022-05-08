package com.andresmastracchio.yoprogramo.adapter.repository;

import com.andresmastracchio.yoprogramo.entity.Role;
import com.andresmastracchio.yoprogramo.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}

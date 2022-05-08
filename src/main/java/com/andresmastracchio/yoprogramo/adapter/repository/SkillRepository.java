package com.andresmastracchio.yoprogramo.adapter.repository;

import com.andresmastracchio.yoprogramo.entity.Education;
import com.andresmastracchio.yoprogramo.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    Optional<Skill> findById(int id);
}

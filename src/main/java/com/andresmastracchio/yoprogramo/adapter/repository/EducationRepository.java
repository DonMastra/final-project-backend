package com.andresmastracchio.yoprogramo.adapter.repository;

import com.andresmastracchio.yoprogramo.entity.Education;
import com.andresmastracchio.yoprogramo.entity.ProfessionalExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {
    Optional<Education> findById(int id);
}

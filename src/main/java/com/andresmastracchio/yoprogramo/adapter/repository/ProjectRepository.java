package com.andresmastracchio.yoprogramo.adapter.repository;

import com.andresmastracchio.yoprogramo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    boolean existsByProjectTitle(String projectTitle);
}

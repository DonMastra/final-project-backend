package com.andresmastracchio.yoprogramo.usecase.impl;

import com.andresmastracchio.yoprogramo.adapter.repository.ProjectRepository;
import com.andresmastracchio.yoprogramo.entity.ProfessionalExperience;
import com.andresmastracchio.yoprogramo.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(
            ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public boolean existsByProjectTitle(String projectTitle) {
        return projectRepository.existsByProjectTitle(projectTitle);
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public boolean existsById(Integer id) {
        return projectRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        projectRepository.deleteById(id);
    }

    public Project getById(Integer id) {
        return projectRepository.getById(id);
    }
}

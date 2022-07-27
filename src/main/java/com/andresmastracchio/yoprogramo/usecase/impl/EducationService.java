package com.andresmastracchio.yoprogramo.usecase.impl;

import com.andresmastracchio.yoprogramo.adapter.repository.EducationRepository;
import com.andresmastracchio.yoprogramo.adapter.repository.ProfessionalExperienceRepository;
import com.andresmastracchio.yoprogramo.entity.Education;
import com.andresmastracchio.yoprogramo.entity.ProfessionalExperience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EducationService {

    private final EducationRepository educationRepository;

    @Autowired
    public EducationService(
            EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public List<Education> getAllStudies() {
        return educationRepository.findAll();
    }

    public boolean existsByTitle(String studyTitle) {
        return educationRepository.existsByStudyTitle(studyTitle);
    }

    public Education save(Education education) {
        return educationRepository.save(education);
    }

    public boolean existsById(Integer id) {
        return educationRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        educationRepository.deleteById(id);
    }

    public Education getById(Integer id) {
        return educationRepository.getById(id);
    }
}

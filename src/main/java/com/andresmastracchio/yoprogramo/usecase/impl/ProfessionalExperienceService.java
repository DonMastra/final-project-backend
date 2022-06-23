package com.andresmastracchio.yoprogramo.usecase.impl;

import com.andresmastracchio.yoprogramo.adapter.repository.ProfessionalExperienceRepository;
import com.andresmastracchio.yoprogramo.entity.Education;
import com.andresmastracchio.yoprogramo.entity.ProfessionalExperience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class ProfessionalExperienceService {

    private final ProfessionalExperienceRepository professionalExperienceRepository;

    @Autowired
    public ProfessionalExperienceService(
            ProfessionalExperienceRepository professionalExperienceRepository) {
        this.professionalExperienceRepository = professionalExperienceRepository;
    }

    public List<ProfessionalExperience> getAllExperiences() {
        return professionalExperienceRepository.findAll();
    }

    public boolean existsByJobTitle(String jobTitle) {
        return professionalExperienceRepository.existsByJobTitle(jobTitle);
    }

    public ProfessionalExperience save(ProfessionalExperience professionalExperience) {
        return professionalExperienceRepository.save(professionalExperience);
    }

    public boolean existsById(Integer id) {
        return professionalExperienceRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        professionalExperienceRepository.deleteById(id);
    }

    public ProfessionalExperience getById(Integer id) {
        return professionalExperienceRepository.getById(id);
    }
}

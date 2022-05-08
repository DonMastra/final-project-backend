package com.andresmastracchio.yoprogramo.usecase.impl;

import com.andresmastracchio.yoprogramo.adapter.repository.ProfessionalExperienceRepository;
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
}

package com.andresmastracchio.yoprogramo.usecase.impl;

import com.andresmastracchio.yoprogramo.adapter.repository.SkillRepository;
import com.andresmastracchio.yoprogramo.entity.Project;
import com.andresmastracchio.yoprogramo.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class SkillService {

    private final SkillRepository skillRepository;

    @Autowired
    public SkillService(
            SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public boolean existsBySkillName(String skillName) {
        return skillRepository.existsBySkillName(skillName);
    }

    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }

    public boolean existsById(Integer id) {
        return skillRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        skillRepository.deleteById(id);
    }

    public Skill getById(Integer id) {
        return skillRepository.getById(id);
    }
}

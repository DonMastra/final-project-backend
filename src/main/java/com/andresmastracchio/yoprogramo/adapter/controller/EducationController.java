package com.andresmastracchio.yoprogramo.adapter.controller;

import com.andresmastracchio.yoprogramo.dto.MessageDto;
import com.andresmastracchio.yoprogramo.entity.Education;
import com.andresmastracchio.yoprogramo.entity.ProfessionalExperience;
import com.andresmastracchio.yoprogramo.entity.Project;
import com.andresmastracchio.yoprogramo.entity.Skill;
import com.andresmastracchio.yoprogramo.usecase.impl.EducationService;
import com.andresmastracchio.yoprogramo.usecase.impl.ProfessionalExperienceService;
import com.andresmastracchio.yoprogramo.usecase.impl.ProjectService;
import com.andresmastracchio.yoprogramo.usecase.impl.SkillService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/education")
@CrossOrigin(origins = "http://localhost:4200")
public class EducationController {

    private final EducationService educationService;

    @Autowired
    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping("/education-list")
    public ResponseEntity<List<Education>> getAllEducationComponents() {
        List<Education> allEducation = educationService.getAllStudies();
        return new ResponseEntity<>(allEducation, HttpStatus.OK);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Education education) {
        if (StringUtils.isBlank(education.getDescription())) {
            return new ResponseEntity(new MessageDto("la descripción es obligatoria"), HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isBlank(education.getStudyTitle())) {
            return new ResponseEntity(new MessageDto("el título de estudio es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        if (educationService.existsByTitle(education.getStudyTitle())) {
            return new ResponseEntity(new MessageDto("ese titulo ya existe"), HttpStatus.BAD_REQUEST);
        }

        educationService.save(education);
        return new ResponseEntity(new MessageDto("Educación guardada"), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Education education, @PathVariable("id") Integer id) {
        if (!educationService.existsById(id)) {
            return new ResponseEntity(new MessageDto("no existe ese education"), HttpStatus.NOT_FOUND);
        }

        if (StringUtils.isBlank(education.getDescription())) {
            return new ResponseEntity(new MessageDto("la descripción es obligatoria"), HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isBlank(education.getStudyTitle())) {
            return new ResponseEntity(new MessageDto("el título de estudio es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        if (educationService.existsByTitle(education.getStudyTitle())) {
            return new ResponseEntity(new MessageDto("ese titulo ya existe"), HttpStatus.BAD_REQUEST);
        }

        Education educationUpdate = educationService.getById(id);
        educationUpdate.setDescription(education.getDescription());
        educationUpdate.setStudyTitle(education.getStudyTitle());
        educationUpdate.setFromDate(education.getFromDate());
        educationUpdate.setToDate(education.getToDate());
        educationService.save(educationUpdate);
        return new ResponseEntity(new MessageDto("education actualizado"), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (!educationService.existsById(id)) {
            return new ResponseEntity(new MessageDto("no existe ese titulo"), HttpStatus.NOT_FOUND);
        }
        educationService.deleteById(id);
        return new ResponseEntity(new MessageDto("producto eliminado"), HttpStatus.OK);
    }


}

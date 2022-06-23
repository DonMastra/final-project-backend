package com.andresmastracchio.yoprogramo.adapter.controller;

import com.andresmastracchio.yoprogramo.dto.MessageDto;
import com.andresmastracchio.yoprogramo.entity.ProfessionalExperience;
import com.andresmastracchio.yoprogramo.usecase.impl.ProfessionalExperienceService;
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
@RequestMapping("/api/exp")
@CrossOrigin(origins = "http://localhost:4200")
public class ExperienceController {

    private final ProfessionalExperienceService professionalExperienceService;

    @Autowired
    public ExperienceController(ProfessionalExperienceService professionalExperienceService) {
        this.professionalExperienceService = professionalExperienceService;
    }

    @GetMapping("/experiences")
    public ResponseEntity<List<ProfessionalExperience>> getAllExperienceComponents() {
        List<ProfessionalExperience> experiences = professionalExperienceService.getAllExperiences();
        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody ProfessionalExperience professionalExperience) {
        if (StringUtils.isBlank(professionalExperience.getJobTitle())) {
            return new ResponseEntity(new MessageDto("el titulo de la experiencia laboral es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isBlank(professionalExperience.getDescription())) {
            return new ResponseEntity(new MessageDto("la descripción es obligatoria"), HttpStatus.BAD_REQUEST);
        }

        if (professionalExperienceService.existsByJobTitle(professionalExperience.getJobTitle())) {
            return new ResponseEntity(new MessageDto("ese título ya existe"), HttpStatus.BAD_REQUEST);
        }

        professionalExperienceService.save(professionalExperience);
        return new ResponseEntity(new MessageDto("producto guardado"), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody ProfessionalExperience professionalExperience,
                                    @PathVariable("id") Integer id) {
        if (!professionalExperienceService.existsById(id)) {
            return new ResponseEntity(new MessageDto("no existe esa experiencia"), HttpStatus.NOT_FOUND);
        }

        if (StringUtils.isBlank(professionalExperience.getJobTitle())) {
            return new ResponseEntity(new MessageDto("el titulo de la experiencia laboral es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isBlank(professionalExperience.getDescription())) {
            return new ResponseEntity(new MessageDto("la descripción es obligatoria"), HttpStatus.BAD_REQUEST);
        }

        if (professionalExperienceService.existsByJobTitle(professionalExperience.getJobTitle())) {
            return new ResponseEntity(new MessageDto("ese experiencia ya existe"), HttpStatus.BAD_REQUEST);
        }
        ProfessionalExperience expUpdate = professionalExperienceService.getById(id);
        expUpdate.setJobTitle(professionalExperience.getJobTitle());
        expUpdate.setDescription(professionalExperience.getDescription());
        expUpdate.setFromDate(professionalExperience.getFromDate());
        expUpdate.setToDate(professionalExperience.getToDate());

        professionalExperienceService.save(expUpdate);
        return new ResponseEntity(new MessageDto("experiencia laboral actualizada"), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (!professionalExperienceService.existsById(id)) {
            return new ResponseEntity(new MessageDto("no existe esa experiencia"), HttpStatus.NOT_FOUND);
        }
        professionalExperienceService.deleteById(id);
        return new ResponseEntity(new MessageDto("experiencia eliminada"), HttpStatus.OK);
    }


}

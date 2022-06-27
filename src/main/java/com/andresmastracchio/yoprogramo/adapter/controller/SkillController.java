package com.andresmastracchio.yoprogramo.adapter.controller;

import com.andresmastracchio.yoprogramo.dto.MessageDto;
import com.andresmastracchio.yoprogramo.entity.Skill;
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
@RequestMapping("/api/skill")
@CrossOrigin(origins = "http://localhost:4200")
public class SkillController {

    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getAllSkills() {
        List<Skill> skills = skillService.getAllSkills();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Skill skill) {
        if (StringUtils.isBlank(skill.getSkillName())) {
            return new ResponseEntity(new MessageDto("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        if (skillService.existsBySkillName(skill.getSkillName())) {
            return new ResponseEntity(new MessageDto("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }

        skillService.save(skill);
        return new ResponseEntity(new MessageDto("skill guardada"), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Skill skill, @PathVariable("id") Integer id) {
        if (!skillService.existsById(id)) {
            return new ResponseEntity(new MessageDto("no existe ese skill"), HttpStatus.NOT_FOUND);
        }
        if (StringUtils.isBlank(skill.getSkillName())) {
            return new ResponseEntity(new MessageDto("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        /*if (skillService.existsBySkillName(skill.getSkillName()) &&
                skillService.obtenerPorNombre(skill.getNombreProducto()).get().getId() != id) {
            return new ResponseEntity(new MessageDto("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }*/

        Skill skillUpdate = skillService.getById(id);
        skillUpdate.setSkillName(skill.getSkillName());
        return new ResponseEntity(new MessageDto("skill actualizado"), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (!skillService.existsById(id)) {
            return new ResponseEntity(new MessageDto("no existe esa skill"), HttpStatus.NOT_FOUND);
        }
        skillService.deleteById(id);
        return new ResponseEntity(new MessageDto("skill eliminada"), HttpStatus.OK);
    }
}

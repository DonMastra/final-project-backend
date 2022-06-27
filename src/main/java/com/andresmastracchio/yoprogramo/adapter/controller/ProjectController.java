package com.andresmastracchio.yoprogramo.adapter.controller;

import com.andresmastracchio.yoprogramo.dto.MessageDto;
import com.andresmastracchio.yoprogramo.entity.Project;
import com.andresmastracchio.yoprogramo.usecase.impl.ProjectService;
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
@RequestMapping("/api/project")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Project project) {
        if (StringUtils.isBlank(project.getProjectTitle())) {
            return new ResponseEntity(new MessageDto("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isBlank(project.getDescription())) {
            return new ResponseEntity(new MessageDto("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        if (projectService.existsByProjectTitle(project.getProjectTitle())) {
            return new ResponseEntity(new MessageDto("ese nombre de proyecto ya existe"), HttpStatus.BAD_REQUEST);
        }

        projectService.save(project);
        return new ResponseEntity(new MessageDto("project guardado"), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Project project, @PathVariable("id") Integer id) {
        if (!projectService.existsById(id)) {
            return new ResponseEntity(new MessageDto("no existe el proyecto indicado"), HttpStatus.NOT_FOUND);
        }

        if (StringUtils.isBlank(project.getProjectTitle())) {
            return new ResponseEntity(new MessageDto("el nombre del proyecto es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isBlank(project.getDescription())) {
            return new ResponseEntity(new MessageDto("la descripcion es obligatoria"), HttpStatus.BAD_REQUEST);
        }

        Project projectUpdate = projectService.getById(id);
        projectUpdate.setProjectTitle(project.getProjectTitle());
        projectUpdate.setDescription(project.getDescription());
        projectUpdate.setProjectDate(project.getProjectDate());
        projectService.save(projectUpdate);
        return new ResponseEntity(new MessageDto("proyecto actualizado"), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (!projectService.existsById(id)) {
            return new ResponseEntity(new MessageDto("no existe ese proyecto"), HttpStatus.NOT_FOUND);
        }
        projectService.deleteById(id);
        return new ResponseEntity(new MessageDto("proyecto eliminado"), HttpStatus.OK);
    }

}

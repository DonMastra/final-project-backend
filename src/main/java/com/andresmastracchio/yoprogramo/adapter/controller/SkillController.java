package com.andresmastracchio.yoprogramo.adapter.controller;

import com.andresmastracchio.yoprogramo.entity.Skill;
import com.andresmastracchio.yoprogramo.usecase.impl.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    // Test case controllers
    /*
    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Producto producto) {
        if (StringUtils.isBlank(producto.getNombreProducto())) {
            return new ResponseEntity(new MessageDto("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (producto.getPrecio() == 0) {
            return new ResponseEntity(new MessageDto("el precio es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (productoService.existePorNombre(producto.getNombreProducto())) {
            return new ResponseEntity(new MessageDto("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }

        productoService.guardar(producto);
        return new ResponseEntity(new MessageDto("producto guardado"), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Producto producto, @PathVariable("id") Long id) {
        if (!productoService.existePorId(id)) {
            return new ResponseEntity(new MessageDto("no existe ese producto"), HttpStatus.NOT_FOUND);
        }
        if (StringUtils.isBlank(producto.getNombreProducto())) {
            return new ResponseEntity(new MessageDto("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (producto.getPrecio() == 0) {
            return new ResponseEntity(new MessageDto("el precio es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (productoService.existePorNombre(producto.getNombreProducto()) &&
                productoService.obtenerPorNombre(producto.getNombreProducto()).get().getId() != id) {
            return new ResponseEntity(new MessageDto("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        Producto prodUpdate = productoService.obtenerPorId(id).get();
        prodUpdate.setNombreProducto(producto.getNombreProducto());
        prodUpdate.setPrecio(producto.getPrecio());
        productoService.guardar(prodUpdate);
        return new ResponseEntity(new MessageDto("producto actualizado"), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!productoService.existePorId(id)) {
            return new ResponseEntity(new MessageDto("no existe ese producto"), HttpStatus.NOT_FOUND);
        }
        productoService.borrar(id);
        return new ResponseEntity(new MessageDto("producto eliminado"), HttpStatus.OK);
    }

    */
}

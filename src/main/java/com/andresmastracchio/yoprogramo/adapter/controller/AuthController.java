package com.andresmastracchio.yoprogramo.adapter.controller;

import com.andresmastracchio.yoprogramo.config.security.jwt.JwtProvider;
import com.andresmastracchio.yoprogramo.dto.JwtDto;
import com.andresmastracchio.yoprogramo.dto.MessageDto;
import com.andresmastracchio.yoprogramo.dto.NewUserDto;
import com.andresmastracchio.yoprogramo.dto.UserLoginDto;
import com.andresmastracchio.yoprogramo.entity.Role;
import com.andresmastracchio.yoprogramo.entity.RoleName;
import com.andresmastracchio.yoprogramo.entity.User;
import com.andresmastracchio.yoprogramo.usecase.impl.RoleService;
import com.andresmastracchio.yoprogramo.usecase.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final JwtProvider jwtProvider;

    public AuthController(PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          UserService userService,
                          RoleService roleService,
                          JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/new")
    public ResponseEntity<?> newUser(@Valid @RequestBody NewUserDto newUser,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new MessageDto("campos vacíos o email inválido"), HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByName(newUser.getUsername())) {
            return new ResponseEntity(new MessageDto("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByEmail(newUser.getEmail())) {
            return new ResponseEntity(new MessageDto("ese email ya existe"), HttpStatus.BAD_REQUEST);
        }

        User user = new User(newUser.getUsername(), newUser.getEmail(), passwordEncoder.encode(newUser.getPassword()));
        Set<String> rolesStr = newUser.getRoles();
        Set<Role> roles = new HashSet<>();
        for (String role : rolesStr) {
            if ("admin".equals(role)) {
                Role roleAdmin = roleService.getByRoleName(RoleName.ROLE_ADMIN).get();
                roles.add(roleAdmin);
            } else {
                Role roleUser = roleService.getByRoleName(RoleName.ROLE_USER).get();
                roles.add(roleUser);
            }
        }

        user.setRoles(roles);
        userService.saveUser(user);
        return new ResponseEntity(new MessageDto("usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> loginUser(@Valid @RequestBody UserLoginDto userLogin, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new MessageDto("campos vacíos o email inválido"), HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
    }
}

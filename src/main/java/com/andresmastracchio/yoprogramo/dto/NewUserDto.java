package com.andresmastracchio.yoprogramo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.util.Set;

@Getter
@Setter
public class NewUserDto {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private Set<String> roles;
}

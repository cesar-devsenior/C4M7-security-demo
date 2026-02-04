package com.devsenior.cdiaz.security.model.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UpdateUserDto(
    @NotEmpty(message = "El nombre de usuario es obligatorio")
    String username,
    
    @NotEmpty(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no es válido")
    String email,
    
    String name,

    @NotEmpty(message = "La lista de roles son obligatorias")
    List<String> roles) {
}

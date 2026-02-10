package com.devsenior.cdiaz.security.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
    @NotEmpty(message = "El nombre de usuario es obligatorio")
    String username,
    
    @NotEmpty(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener minimo 6 caracteres")
    String password,
    
    @NotEmpty(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no es válido")
    String email,
    
    String name) {
}

package com.devsenior.cdiaz.security.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
        @NotBlank(message = "El nombre de usaurio es obligatorio")
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe contener minimo 6 caracteres")
        String password) {

}

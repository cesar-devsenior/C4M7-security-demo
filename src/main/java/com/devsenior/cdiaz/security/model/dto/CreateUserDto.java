package com.devsenior.cdiaz.security.model.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
    @NotEmpty(message = "El nombre de usuario es obligatorio")
    String username,
    
    @NotEmpty(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener minimo 6 caracteres")
    String password,
    
    @NotEmpty(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no es válido")
    String email,
    
    String name,

    @NotEmpty(message = "La lista de roles son obligatorias")
    List<String> roles) {
}

// public class InnerCreateUserDto {
//     private String username;
//     private String password;
//
//     public InnerCreateUserDto(String username, String password){
//         this.username = username;
//         this.password = password;
//     }
//
//     public String getUsername() {
//         return username;
//     }
//
//     public String getPassword() {
//         return password;
//     }
// }
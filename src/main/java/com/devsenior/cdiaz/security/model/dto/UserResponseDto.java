package com.devsenior.cdiaz.security.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public record UserResponseDto(
    String username,

    String email,

    String name,

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("hire_date")
    LocalDate hireDate,

    Boolean active,

    List<String> roles) {
    
}

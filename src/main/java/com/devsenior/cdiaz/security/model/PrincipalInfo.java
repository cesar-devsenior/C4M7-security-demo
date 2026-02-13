package com.devsenior.cdiaz.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PrincipalInfo {
    private String username;
    private String email;
    private String name;
    private String hireDate;
}

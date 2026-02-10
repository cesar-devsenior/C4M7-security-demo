package com.devsenior.cdiaz.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devsenior.cdiaz.security.model.dto.LoginRequestDto;
import com.devsenior.cdiaz.security.model.dto.LoginResponseDto;
import com.devsenior.cdiaz.security.model.dto.RegisterRequestDto;
import com.devsenior.cdiaz.security.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto dto) {
        return authService.login(dto);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequestDto dto) {
        authService.register(dto);
    }
    
}

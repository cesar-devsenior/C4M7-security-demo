package com.devsenior.cdiaz.security.service;

import com.devsenior.cdiaz.security.model.dto.LoginRequestDto;
import com.devsenior.cdiaz.security.model.dto.LoginResponseDto;
import com.devsenior.cdiaz.security.model.dto.RegisterRequestDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto credentials);

    void register(RegisterRequestDto info);
}

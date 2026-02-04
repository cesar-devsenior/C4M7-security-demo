package com.devsenior.cdiaz.security.service;

import java.util.List;

import com.devsenior.cdiaz.security.model.dto.CreateUserDto;
import com.devsenior.cdiaz.security.model.dto.UpdateUserDto;
import com.devsenior.cdiaz.security.model.dto.UserResponseDto;

public interface UserService {
    
    List<UserResponseDto> getAll();

    UserResponseDto create(CreateUserDto dto);

    UserResponseDto update(UpdateUserDto dto);

    void activate(String username);

    void deactivate(String username);

}

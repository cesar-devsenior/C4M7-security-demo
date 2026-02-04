package com.devsenior.cdiaz.security.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devsenior.cdiaz.security.model.dto.CreateUserDto;
import com.devsenior.cdiaz.security.model.dto.UpdateUserDto;
import com.devsenior.cdiaz.security.model.dto.UserResponseDto;
import com.devsenior.cdiaz.security.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;




@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public UserResponseDto create(@RequestBody @Valid CreateUserDto dto) {
        return userService.create(dto);
    }

    @PutMapping("/{username}")
    public UserResponseDto update(@PathVariable String username, @RequestBody @Valid UpdateUserDto dto) {
        return userService.update(dto);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PatchMapping("/{username}/activate")
    public void activate(@PathVariable String username) {
        userService.activate(username);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PatchMapping("/{username}/deactivate")
    public void deactivate(@PathVariable String username) {
        userService.deactivate(username);
    }
}

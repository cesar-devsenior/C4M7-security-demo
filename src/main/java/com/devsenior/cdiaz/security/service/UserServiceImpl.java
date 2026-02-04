package com.devsenior.cdiaz.security.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devsenior.cdiaz.security.mapper.UserMapper;
import com.devsenior.cdiaz.security.model.dto.CreateUserDto;
import com.devsenior.cdiaz.security.model.dto.UpdateUserDto;
import com.devsenior.cdiaz.security.model.dto.UserResponseDto;
import com.devsenior.cdiaz.security.repository.RoleRepository;
import com.devsenior.cdiaz.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
        var user = userMapper.toEntity(dto);
        user.setHireDate(LocalDate.now());
        user.setActive(true);

        var roles = dto.roles().stream()
                .map(r -> roleRepository.findByName(r).orElse(null))
                .filter(r -> r != null)
                .toList();

        user.setRoles(roles);

        user = userRepository.save(user);

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponseDto update(UpdateUserDto dto) {
        var actualUser = userRepository.findById(dto.username())
                .orElseThrow();

        var user = userMapper.toEntity(dto);
        user.setPassword(actualUser.getPassword());
        user.setHireDate(actualUser.getHireDate());
        user.setActive(actualUser.getActive());

        var roles = dto.roles().stream()
                .map(r -> roleRepository.findByName(r).orElse(null))
                .filter(r -> r != null)
                .toList();

        user.setRoles(roles);

        user = userRepository.save(user);

        return userMapper.toResponse(user);
    }

    @Override
    public void activate(String username) {
        var user = userRepository.findById(username)
                .orElseThrow();

        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void deactivate(String username) {
        var user = userRepository.findById(username)
                .orElseThrow();

        user.setActive(false);
        userRepository.save(user);
    }

}

package com.devsenior.cdiaz.security.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsenior.cdiaz.security.mapper.UserMapper;
import com.devsenior.cdiaz.security.model.dto.LoginRequestDto;
import com.devsenior.cdiaz.security.model.dto.LoginResponseDto;
import com.devsenior.cdiaz.security.model.dto.RegisterRequestDto;
import com.devsenior.cdiaz.security.model.entity.RoleEntity;
import com.devsenior.cdiaz.security.repository.RoleRepository;
import com.devsenior.cdiaz.security.repository.UserRepository;
import com.devsenior.cdiaz.security.util.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto credentials) {
        log.info("Ingresando a iniciar sesion");
        try {
            var auth = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
            authenticationManager.authenticate(auth);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Credenciales inválidas");
        }

        var user = userRepository.findById(credentials.username()).get();

        var token = jwtUtils.generateToken(credentials.username(), Map.of(
                "email", user.getEmail(),
                "name", user.getName(),
                "hire_date", user.getHireDate().format(DateTimeFormatter.ISO_DATE),
                "roles", user.getRoles().stream()
                        .map(RoleEntity::getName)
                        .toList()));

        log.info("Token: {}", token);

        return new LoginResponseDto(token, "JWT");
    }

    @Override
    public void register(RegisterRequestDto info) {
        if (userRepository.existsById(info.username())) {
            throw new RuntimeException(String.format("El usuario '%s' ya existe en el sistema", info.username()));
        }

        var user = userMapper.toEntity(info);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setHireDate(LocalDate.now());
        user.setActive(true);

        var role = roleRepository.findByName("GUEST")
                .orElseThrow(() -> new RuntimeException("El rol USER no existe en el sistema"));
        user.setRoles(List.of(role));

        userRepository.save(user);
    }

}

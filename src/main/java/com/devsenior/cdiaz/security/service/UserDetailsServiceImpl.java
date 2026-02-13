package com.devsenior.cdiaz.security.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsenior.cdiaz.security.model.entity.RoleEntity;
import com.devsenior.cdiaz.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));

        var roles = user.getRoles().stream()
                .map(RoleEntity::getName)
                .map(s -> "ROLE_"+s)
                .map(SimpleGrantedAuthority::new)
                .toList();

        return User.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(roles)
                .build();
    }

}

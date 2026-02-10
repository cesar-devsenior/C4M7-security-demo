package com.devsenior.cdiaz.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsenior.cdiaz.security.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}

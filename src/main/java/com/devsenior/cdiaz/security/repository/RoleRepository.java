package com.devsenior.cdiaz.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsenior.cdiaz.security.model.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
 
    Optional<RoleEntity> findByName(String name);
}

package com.devsenior.cdiaz.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.devsenior.cdiaz.security.model.dto.CreateUserDto;
import com.devsenior.cdiaz.security.model.dto.UpdateUserDto;
import com.devsenior.cdiaz.security.model.dto.UserResponseDto;
import com.devsenior.cdiaz.security.model.entity.RoleEntity;
import com.devsenior.cdiaz.security.model.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
        
    @Mapping(target = "hireDate", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserEntity toEntity(CreateUserDto dto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "hireDate", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserEntity toEntity(UpdateUserDto dto);

    UserResponseDto toResponse(UserEntity entity);

    default String roleToString(RoleEntity role) {
        return (role == null) ? "" : role.getName();
    }
}

package com.devsenior.cdiaz.security.model.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.type.YesNoConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    
    @Id
    @Column(length = 50)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "full_name")
    private String name;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Convert(converter = YesNoConverter.class)
    private Boolean active;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(columnDefinition = "username"),
        inverseJoinColumns = @JoinColumn(columnDefinition = "role_id")
    )
    private List<RoleEntity> roles;
}

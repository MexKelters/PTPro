package com.ptpro.mapper;

import com.ptpro.dto.request.*;
import com.ptpro.dto.response.UserResponse;
import com.ptpro.model.Role;
import com.ptpro.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @PersistenceContext
    protected EntityManager entityManager;

    @Mapping(target = "role", source = "roleId")
    public abstract User toEntity(CreateUserRequest dto);

    public abstract UserResponse toResponse(User user);

    // Helper: roleId -> Role
    protected Role map(Long roleId) {
        return entityManager.getReference(Role.class, roleId);
    }
}
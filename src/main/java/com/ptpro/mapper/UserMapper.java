package com.ptpro.mapper;

import com.ptpro.dto.request.CreateUserRequest;
import com.ptpro.dto.request.UpdateUserRequest;
import com.ptpro.dto.response.UserResponse;
import com.ptpro.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // CREATE
    @org.mapstruct.Mapping(target = "id", ignore = true)   // 👈 negeer id
    @org.mapstruct.Mapping(target = "role", ignore = true) // 👈 negeer role, wij zetten het handmatig
    User toEntity(CreateUserRequest dto);

    // UPDATE
    void updateEntity(@MappingTarget User entity, UpdateUserRequest dto);

    // RESPONSE
    UserResponse toResponse(User entity);
}
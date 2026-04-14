package com.ptpro.mapper;

import com.ptpro.dto.request.CreateRoleRequest;
import com.ptpro.dto.request.UpdateRoleRequest;
import com.ptpro.dto.response.RoleResponse;
import com.ptpro.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    // CREATE
    Role toEntity(CreateRoleRequest dto);

    // UPDATE
    void updateEntity(@MappingTarget Role entity, UpdateRoleRequest dto);

    // RESPONSE
    RoleResponse toResponse(Role entity);
}
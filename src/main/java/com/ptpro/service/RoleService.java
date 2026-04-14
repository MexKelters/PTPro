package com.ptpro.service;

import com.ptpro.dto.request.CreateRoleRequest;
import com.ptpro.dto.request.UpdateRoleRequest;
import com.ptpro.dto.response.RoleResponse;
import com.ptpro.mapper.RoleMapper;
import com.ptpro.model.Role;
import com.ptpro.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toList());
    }

    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role niet gevonden met id: " + id));
        return roleMapper.toResponse(role);
    }

    public RoleResponse createRole(CreateRoleRequest request) {
        Role role = roleMapper.toEntity(request);
        return roleMapper.toResponse(roleRepository.save(role));
    }

    public RoleResponse updateRole(Long id, UpdateRoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role niet gevonden met id: " + id));
        roleMapper.updateEntity(role, request);
        return roleMapper.toResponse(roleRepository.save(role));
    }

    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role niet gevonden met id: " + id);
        }
        roleRepository.deleteById(id);
    }
}
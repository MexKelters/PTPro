package com.ptpro.service;


import com.ptpro.dto.request.CreateUserRequest;
import com.ptpro.dto.request.UpdateUserRequest;
import com.ptpro.dto.response.UserResponse;
import com.ptpro.mapper.UserMapper;
import com.ptpro.model.Role;
import com.ptpro.model.User;
import com.ptpro.repository.RoleRepository;
import com.ptpro.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    final private UserRepository userRepository;
    final private UserMapper userMapper;
    final private RoleRepository roleRepository;
    final private EntityManager entityManager;

    public UserService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.entityManager = entityManager;
    }


    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> dtos = new ArrayList<>();
        for(User user : users){
            dtos.add(userMapper.toResponse(user));
        }
        return dtos;
    }

    public UserResponse getUserById(Long id) {
        Optional<User> oldUser = userRepository.findById(id);
        if(oldUser.isPresent()){
            return userMapper.toResponse(oldUser.get());
        } else
            return null;
    }

    public UserResponse addUser(CreateUserRequest dto) {
        User newUser = userMapper.toEntity(dto);
        // Role ophalen via DTO
        Long roleId = dto.getRoleId();
        Role role = entityManager.getReference(Role.class, roleId);
        newUser.setRole(role);
        User savedUser = userRepository.save(newUser);
        return userMapper.toResponse(savedUser);
    }

//    public UpdateUserRequest updateUser(Long id, UpdateUserRequest updateUserRequest) {
//        User user = userRepository.findById(id).get();
//        user.setFirstName(updateUserRequest.getFirstName());
//        user.setLastName(updateUserRequest.getLastName());
//        user.setEmail(updateUserRequest.getEmail());
//        userRepository.save(user);
//        return userMapper.toResponse(user);
//    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateEntity(user, request);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public User getOrCreateUser(String email, String firstName, String lastName, Collection<GrantedAuthority> role) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setFirstName(firstName);
                    newUser.setLastName(lastName);
                    //Hoe cast ik dit naar een Role entity
                    String roleFromArray = Arrays.toString(role.toArray());
                    String roleFinal = roleFromArray.toString();
                    String roleDef = roleFinal.replaceAll("\\[|\\]", "");
                    Role newRole = new Role();
                    roleRepository.save(newRole);
                    newRole.setName(roleDef);

                    // If statement maken en dan de ID van de role aan de user koppelen
                    // Dit moet ik verder uitwerken, want hoe doe ik dat zonder duplicate entry


                    newUser.setRole(newRole);
                    return userRepository.save(newUser);
                });
    }
}

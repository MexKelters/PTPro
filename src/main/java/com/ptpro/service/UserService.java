package com.ptpro.service;


import com.ptpro.dto.request.CreateUserRequest;
import com.ptpro.dto.response.UserResponse;
import com.ptpro.mapper.UserMapper;
import com.ptpro.model.User;
import com.ptpro.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    final private UserRepository userRepository;
    final private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
        User savedUser = userRepository.save(newUser);
        return userMapper.toResponse(savedUser);
    }
}

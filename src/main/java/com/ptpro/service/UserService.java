package com.ptpro.service;


import com.ptpro.dto.response.UserResponse;
import com.ptpro.mapper.UserMapper;
import com.ptpro.model.User;
import com.ptpro.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            dtos.add(userMapper.userToDto(user));
        }
        return dtos;
    }
}

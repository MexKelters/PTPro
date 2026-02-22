package com.ptpro.service;


import com.ptpro.model.User;
import com.ptpro.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    final private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public ResponseEntity<List<User>> getAllUsers() {

    }
}

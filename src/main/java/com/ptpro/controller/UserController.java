package com.ptpro.controller;


import com.ptpro.dto.response.UserResponse;
import com.ptpro.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    //TODO: Moet nog dependency injection toevoegen
    //TODO: Alle <User> veranderen in DTO's
        private final UserService userService;

        public UserController(UserService userService){
            this.userService = userService;
        }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


}

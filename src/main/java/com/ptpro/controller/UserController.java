package com.ptpro.controller;


import com.ptpro.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    //TODO: Moet nog dependency injection toevoegen
    //TODO: Alle <User> veranderen in DTO's


    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers())
    }


}

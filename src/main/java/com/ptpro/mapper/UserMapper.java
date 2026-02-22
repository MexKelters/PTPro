package com.ptpro.mapper;

import com.ptpro.dto.response.UserResponse;
import com.ptpro.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    //Entity to DTO
    public UserResponse userToDto(User user){
        if(user==null){
            return null;
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setFirstNAme(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setTrainer(user.getTrainer());
        userResponse.setRole(user.getRole());
        userResponse.setBookings(user.getBookings());
        userResponse.setTrainingSchedules(user.getTrainingSchedules());

        return userResponse;

    }

    //DTO to entity
    public User dtoToUser(UserResponse userResponse){
        if(userResponse == null){
            return null;
        }

        User newUser = new User();
        newUser.setFirstName(userResponse.getFirstName());
        newUser.setLastName(userResponse.getLastName());
        newUser.setEmail(userResponse.getEmail());
        newUser.setTrainer(userResponse.getTrainer());
        newUser.setRole(userResponse.getRole());
        newUser.setBookings(userResponse.getBookings());
        newUser.setTrainingSchedules(userResponse.getTrainingSchedules());

        return newUser;
    }
}

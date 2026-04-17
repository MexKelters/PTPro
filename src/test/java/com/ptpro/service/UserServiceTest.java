package com.ptpro.service;

import com.ptpro.dto.response.UserResponse;
import com.ptpro.mapper.UserMapper;
import com.ptpro.model.User;
import com.ptpro.repository.RoleRepository;
import com.ptpro.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private EntityManager entityManager;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllUsers_shouldReturnListOfUserResponseDTOs() {
        //Arrange
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Mex");
        user1.setLastName("Kelters");
        user1.setEmail("mex_kelters@hotmail.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Lee");
        user2.setLastName("Kelters");
        user2.setEmail("lee_kelters@hotmail.com");

        List<User> nepUsers = List.of(user1, user2);

        UserResponse userResponse1 = new UserResponse();
        userResponse1.setFirstName("Mex");
        userResponse1.setLastName("Kelters");
        userResponse1.setEmail("mex_kelters@hotmail.com");

        UserResponse userResponse2 = new UserResponse();
        userResponse2.setFirstName("Lee");
        userResponse2.setLastName("Kelters");
        userResponse2 .setEmail("lee_kelters@hotmail.com");

        when(userRepository.findAll()).thenReturn(nepUsers);

        when(userMapper.toResponse(user1)).thenReturn(userResponse1);
        when(userMapper.toResponse(user2)).thenReturn(userResponse2);
        //Act
        List<UserResponse> resultaat = userService.getAllUsers();
        //Assert
        assertEquals(2, resultaat.size());
        assertEquals("Mex", resultaat.get(0).getFirstName());
        assertEquals("Lee", resultaat.get(1).getFirstName());
    }

    @Test
    void getUserById() {
        //Arrange

        //Act
        //Assert
    }

    @Test
    void addUser() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void updateUser() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void deleteUser() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void getOrCreateUser() {
        //Arrange
        //Act
        //Assert
    }
}
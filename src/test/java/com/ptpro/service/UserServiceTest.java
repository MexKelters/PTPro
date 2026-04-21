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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("Mex");
        user.setEmail("mex@hotmail.com");

        UserResponse response = new UserResponse();
        response.setFirstName("Mex");
        response.setEmail("mex@hotmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(response);

        // Act
        UserResponse resultaat = userService.getUserById(1L);

        // Assert
        assertNotNull(resultaat);
        assertEquals("Mex", resultaat.getFirstName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void addUser() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest();
        request.setFirstName("Mex");
        request.setLastName("Kelters");
        request.setEmail("mex@hotmail.com");
        request.setRoleId(2L);

        Role role = new Role();
        role.setId(2L);

        User newUser = new User();
        newUser.setFirstName("Mex");
        newUser.setLastName("Kelters");
        newUser.setEmail("mex@hotmail.com");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setFirstName("Mex");
        savedUser.setLastName("Kelters");
        savedUser.setEmail("mex@hotmail.com");
        savedUser.setRole(role);

        UserResponse response = new UserResponse();
        response.setFirstName("Mex");
        response.setEmail("mex@hotmail.com");

        when(userMapper.toEntity(request)).thenReturn(newUser);
        when(entityManager.getReference(Role.class, 2L)).thenReturn(role);
        when(userRepository.save(newUser)).thenReturn(savedUser);
        when(userMapper.toResponse(savedUser)).thenReturn(response);

        // Act
        UserResponse resultaat = userService.addUser(request);

        // Assert
        assertNotNull(resultaat);
        assertEquals("Mex", resultaat.getFirstName());
        verify(userRepository, times(1)).save(newUser);
        verify(entityManager, times(1)).getReference(Role.class, 2L);
    }

    @Test
    void updateUser() {
        // Arrange
        UpdateUserRequest request = new UpdateUserRequest();
        request.setFirstName("UpdatedName");
        request.setEmail("updated@hotmail.com");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setFirstName("OldName");
        existingUser.setEmail("old@hotmail.com");

        UserResponse response = new UserResponse();
        response.setFirstName("UpdatedName");
        response.setEmail("updated@hotmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userMapper.toResponse(existingUser)).thenReturn(response);

        // Act
        UserResponse resultaat = userService.updateUser(1L, request);

        // Assert
        assertNotNull(resultaat);
        assertEquals("UpdatedName", resultaat.getFirstName());
        verify(userMapper, times(1)).updateEntity(existingUser, request);
        verify(userRepository, times(1)).save(existingUser);
    }


    @Test
    void deleteUser() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void getOrCreateUser_whenUserAlreadyExists_shouldReturnExistingUser() {
        // Arrange
        String email = "mex@hotmail.com";
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail(email);
        existingUser.setFirstName("Mex");
        existingUser.setLastName("Kelters");

        List<GrantedAuthority> authorities = new ArrayList<>();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        // Act
        User resultaat = userService.getOrCreateUser(email, "Mex", "Kelters", authorities);

        // Assert
        assertNotNull(resultaat);
        assertEquals(email, resultaat.getEmail());
        verify(userRepository, never()).save(any());
        verify(entityManager, never()).getReference(any(), any());
    }

    @Test
    void getOrCreateUser_whenUserDoesNotExist_shouldCreateAndSaveNewUser() {
        // Arrange
        String email = "nieuw@hotmail.com";
        Role defaultRole = new Role();
        defaultRole.setId(1L);

        User savedUser = new User();
        savedUser.setId(5L);
        savedUser.setEmail(email);
        savedUser.setFirstName("Nieuw");
        savedUser.setLastName("Gebruiker");
        savedUser.setRole(defaultRole);

        List<GrantedAuthority> authorities = new ArrayList<>();
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(entityManager.getReference(Role.class, 1L)).thenReturn(defaultRole);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User resultaat = userService.getOrCreateUser(email, "Nieuw", "Gebruiker", authorities);

        // Assert
        assertNotNull(resultaat);
        assertEquals(email, resultaat.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verify(entityManager, times(1)).getReference(Role.class, 1L);
    }
}


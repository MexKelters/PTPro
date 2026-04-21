package com.ptpro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptpro.model.Role;
import com.ptpro.model.User;
import com.ptpro.repository.RoleRepository;
import com.ptpro.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();

        Role role = new Role();
        role.setId(1L);
        role.setName("USER");
        roleRepository.save(role);

        User user1 = new User();
        user1.setFirstName("Mex");
        user1.setLastName("Kelters");
        user1.setEmail("mex@hotmail.com");
        user1.setRole(role);
        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Lee");
        user2.setLastName("Kelters");
        user2.setEmail("lee@hotmail.com");
        user2.setRole(role);
        userRepository.save(user2);
    }

    @Test
    void getAllUsers_shouldReturn200AndListOfUsers() throws Exception {
        // Arrange niet nodig

        // Act & Assert
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Mex"))
                .andExpect(jsonPath("$[1].firstName").value("Lee"));
    }

    @Test
    void getUserById_shouldReturn200AndCorrectUser() throws Exception {
        // Arrange
        Long id = userRepository.findByEmail("mex@hotmail.com").get().getId();

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Mex"))
                .andExpect(jsonPath("$.email").value("mex@hotmail.com"));
    }
}
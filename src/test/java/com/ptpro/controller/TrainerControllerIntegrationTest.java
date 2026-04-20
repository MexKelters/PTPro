package com.ptpro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptpro.model.Role;
import com.ptpro.model.Trainer;
import com.ptpro.model.User;
import com.ptpro.repository.RoleRepository;
import com.ptpro.repository.TrainerRepository;
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
class TrainerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        trainerRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        Role role = new Role();
        role.setId(1L);
        role.setName("TRAINER");
        roleRepository.save(role);

        User user = new User();
        user.setFirstName("Mex");
        user.setLastName("Kelters");
        user.setEmail("mex@hotmail.com");
        user.setRole(role);
        userRepository.save(user);

        Trainer trainer = new Trainer();
        trainer.setUser(user);
        trainer.setSpecialization("Krachttraining");
        trainer.setExperience("5 jaar");
        trainer.setDescription("Ervaren personal trainer");
        trainerRepository.save(trainer);
    }

    @Test
    void getAllTrainers_shouldReturn200AndListOfTrainers() throws Exception {
        // Arrange niet nodig

        // Act & Assert
        mockMvc.perform(get("/trainer/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].specialization").value("Krachttraining"));
    }

    @Test
    void getTrainerById_shouldReturn200AndCorrectTrainer() throws Exception {
        // Arrange
        Long id = trainerRepository.findAll().get(0).getId();

        // Act & Assert
        mockMvc.perform(get("/trainer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specialization").value("Krachttraining"))
                .andExpect(jsonPath("$.experience").value("5 jaar"));
    }
}
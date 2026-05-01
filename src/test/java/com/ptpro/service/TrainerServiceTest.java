package com.ptpro.service;

import com.ptpro.dto.request.CreateTrainerRequest;
import com.ptpro.dto.request.UpdateTrainerRequest;
import com.ptpro.dto.response.TrainerResponse;
import com.ptpro.exception.DuplicateResourceException;
import com.ptpro.exception.ResourceNotFoundException;
import com.ptpro.mapper.TrainerMapper;
import com.ptpro.model.Trainer;
import com.ptpro.model.User;
import com.ptpro.repository.TrainerRepository;
import com.ptpro.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainerMapper trainerMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TrainerService trainerService;

    @Test
    void getAllTrainers_shouldReturnListOfTrainerResponses() {
        // Arrange
        Trainer trainer1 = new Trainer();
        trainer1.setId(1L);

        Trainer trainer2 = new Trainer();
        trainer2.setId(2L);

        TrainerResponse response1 = new TrainerResponse();
        TrainerResponse response2 = new TrainerResponse();

        when(trainerRepository.findAll()).thenReturn(List.of(trainer1, trainer2));
        when(trainerMapper.toResponse(trainer1)).thenReturn(response1);
        when(trainerMapper.toResponse(trainer2)).thenReturn(response2);

        // Act
        List<TrainerResponse> resultaat = trainerService.getAllTrainers();

        // Assert
        assertEquals(2, resultaat.size());
        verify(trainerRepository, times(1)).findAll();
    }

    @Test
    void getById_whenTrainerExists_shouldReturnTrainerResponse() {
        // Arrange
        Trainer trainer = new Trainer();
        trainer.setId(1L);

        TrainerResponse response = new TrainerResponse();

        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        when(trainerMapper.toResponse(trainer)).thenReturn(response);

        // Act
        TrainerResponse resultaat = trainerService.getById(1L);

        // Assert
        assertNotNull(resultaat);
        verify(trainerRepository, times(1)).findById(1L);
    }

    @Test
    void addTrainer_whenUserExistsAndNoTrainerYet_shouldSaveAndReturnResponse() {
        // Arrange
        CreateTrainerRequest request = new CreateTrainerRequest();
        request.setUserId(1L);

        User user = new User();
        user.setId(1L);

        Trainer trainer = new Trainer();
        Trainer savedTrainer = new Trainer();
        savedTrainer.setId(10L);

        TrainerResponse response = new TrainerResponse();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(trainerRepository.existsByUserId(1L)).thenReturn(false);
        when(trainerMapper.toEntity(request)).thenReturn(trainer);
        when(trainerRepository.save(trainer)).thenReturn(savedTrainer);
        when(trainerMapper.toResponse(savedTrainer)).thenReturn(response);

        // Act
        TrainerResponse resultaat = trainerService.addTrainer(request);

        // Assert
        assertNotNull(resultaat);
        verify(trainerRepository, times(1)).save(trainer);
    }

    @Test
    void updateTrainer_whenTrainerExists_shouldUpdateAndReturnResponse() {
        // Arrange
        UpdateTrainerRequest request = new UpdateTrainerRequest();

        Trainer trainer = new Trainer();
        trainer.setId(1L);

        TrainerResponse response = new TrainerResponse();

        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        when(trainerMapper.toResponse(trainer)).thenReturn(response);

        // Act
        TrainerResponse resultaat = trainerService.updateTrainer(1L, request);

        // Assert
        assertNotNull(resultaat);
        verify(trainerMapper, times(1)).updateEntity(trainer, request);
        verify(trainerRepository, times(1)).save(trainer);
    }

    @Test
    void deleteTrainer_shouldCallDeleteById() {
        // Arrange
        when(trainerRepository.existsById(1L)).thenReturn(true);
        // Act
        trainerService.deleteTrainer(1L);

        // Assert
        verify(trainerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTrainer_whenTrainerNotFound_shouldThrowResourceNotFoundException() {
        // Arrange
        when(trainerRepository.existsById(111L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> trainerService.deleteTrainer(111L));
        verify(trainerRepository, never()).deleteById(any());
    }

    @Test
    void addTrainer_whenTrainerAlreadyExists_shouldThrowDuplicateResourceException() {
        // Arrange
        CreateTrainerRequest request = new CreateTrainerRequest();
        request.setUserId(1L);

        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(trainerRepository.existsByUserId(1L)).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> trainerService.addTrainer(request));
    }

}
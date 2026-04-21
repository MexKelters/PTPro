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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    private final UserRepository userRepository;

    public TrainerService(TrainerRepository trainerRepository,
                          TrainerMapper trainerMapper,
                          UserRepository userRepository) {
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
        this.userRepository = userRepository;
    }

    public List<TrainerResponse> getAllTrainers() {
        List<Trainer> trainers = trainerRepository.findAll();
        List<TrainerResponse> dtos = new ArrayList<>();
        for (Trainer trainer : trainers) {
            dtos.add(trainerMapper.toResponse(trainer));
        }
        return dtos;
    }

    public TrainerResponse getById(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainer met id: " + id + " niet gevonden" ));
        return trainerMapper.toResponse(trainer);

    }

    public TrainerResponse addTrainer(CreateTrainerRequest dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User niet gevonden met id: " + dto.getUserId()));

        if (trainerRepository.existsByUserId(user.getId())) {
            throw new DuplicateResourceException("Trainer voor deze user bestaat al");
        }

        Trainer trainer = trainerMapper.toEntity(dto);
        trainer.setUser(user);

        Trainer savedTrainer = trainerRepository.save(trainer);
        return trainerMapper.toResponse(savedTrainer);
    }

    public TrainerResponse updateTrainer(Long id, UpdateTrainerRequest dto) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer niet gevonden met id: " + id));
        trainerMapper.updateEntity(trainer, dto);
        trainerRepository.save(trainer);
        return trainerMapper.toResponse(trainer);
    }

    public void deleteTrainer(Long id) {
        if (!trainerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Trainer niet gevonden met id: " + id);
        }
        trainerRepository.deleteById(id);

    }
}
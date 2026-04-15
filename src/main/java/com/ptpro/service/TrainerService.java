package com.ptpro.service;

import com.ptpro.dto.request.CreateTrainerRequest;
import com.ptpro.dto.request.UpdateTrainerRequest;
import com.ptpro.dto.response.TrainerResponse;
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
        Optional<Trainer> trainer = trainerRepository.findById(id);
        if (trainer.isPresent()) {
            return trainerMapper.toResponse(trainer.get());
        } else {
            return null;
        }
    }

    public TrainerResponse addTrainer(CreateTrainerRequest dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User niet gevonden met id: " + dto.getUserId()));

        if (trainerRepository.existsByUserId(user.getId())) {
            throw new RuntimeException("Trainer voor deze user bestaat al");
        }

        Trainer trainer = trainerMapper.toEntity(dto);
        trainer.setUser(user);

        Trainer savedTrainer = trainerRepository.save(trainer);
        return trainerMapper.toResponse(savedTrainer);
    }

    public TrainerResponse updateTrainer(Long id, UpdateTrainerRequest dto) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer niet gevonden met id: " + id));
        trainerMapper.updateEntity(trainer, dto);
        trainerRepository.save(trainer);
        return trainerMapper.toResponse(trainer);
    }

    public void deleteTrainer(Long id) {
        trainerRepository.deleteById(id);
    }
}
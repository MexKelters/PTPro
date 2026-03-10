package com.ptpro.service;


import com.ptpro.dto.request.CreateTrainerRequest;
import com.ptpro.dto.request.UpdateTrainerRequest;
import com.ptpro.dto.response.TrainerResponse;
import com.ptpro.mapper.TrainerMapper;
import com.ptpro.model.Trainer;
import com.ptpro.model.User;
import com.ptpro.repository.TrainerRepository;
import com.ptpro.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    private final UserRepository userRepository;

    public TrainerService(TrainerRepository trainerRepository, TrainerMapper trainerMapper, UserRepository userRepository) {
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
        this.userRepository = userRepository;
    }

    public List<TrainerResponse> getAllTrainers() {
        List <Trainer> trainers = trainerRepository.findAll();
        List<TrainerResponse> trainerResponses = new ArrayList<>();
        for(Trainer trainer : trainers) {
            trainerResponses.add(trainerMapper.toResponse(trainer));
        }
        return trainerResponses;
    }

    public TrainerResponse getById(Long id) {
        Trainer trainer = trainerRepository.findById(id).orElse(null);
        return trainerMapper.toResponse(trainer);
    }

    public CreateTrainerRequest addTrainer(CreateTrainerRequest dto) {

        //Mapper nu niet gebruiken
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found"));
        if(trainerRepository.existsByUserId(user.getId())){
            throw new RuntimeException("Trainer for this user already exists");
        }
        Trainer trainer = new Trainer();
        trainer.setUser(user);
        trainer.setSpecialization(dto.getSpecialization());
        trainer.setExperience(dto.getExperience());
        trainer.setDescription(dto.getDescription());
        System.out.println(trainer);

        Trainer saved = trainerRepository.save(trainer);

        CreateTrainerRequest response = new CreateTrainerRequest();
        response.setUserId(saved.getUser().getId());
        response.setSpecialization(saved.getSpecialization());
        response.setExperience(saved.getExperience());
        response.setDescription(saved.getDescription());
        return response;
    }

    public TrainerResponse updateTrainer(Long id, UpdateTrainerRequest updateTrainerRequest) {
        Trainer trainer = trainerRepository.findById(id).orElseThrow(()-> new RuntimeException("Trainer not found"));
        trainerMapper.updateEntity(trainer, updateTrainerRequest);
        trainerRepository.save(trainer);
        return trainerMapper.toResponse(trainer);
    }

    public void deleteTrainer(Long id) {
        trainerRepository.deleteById(id);
    }
}

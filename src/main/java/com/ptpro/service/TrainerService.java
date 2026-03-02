package com.ptpro.service;


import com.ptpro.dto.response.TrainerResponse;
import com.ptpro.mapper.TrainerMapper;
import com.ptpro.model.Trainer;
import com.ptpro.repository.TrainerRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    public TrainerService(TrainerRepository trainerRepository, TrainerMapper trainerMapper) {
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
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
}

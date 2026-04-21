package com.ptpro.controller;


import com.ptpro.dto.request.CreateTrainerRequest;
import com.ptpro.dto.request.UpdateTrainerRequest;
import com.ptpro.dto.response.TrainerResponse;
import com.ptpro.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    //FE-8
    @GetMapping
    public ResponseEntity<List<TrainerResponse>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    //FE-26
    @GetMapping("/{id}")
    public ResponseEntity<TrainerResponse> getTrainerById(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getById(id));
    }

    //FE-7
    @PostMapping
    public ResponseEntity<TrainerResponse> addTrainer(@Valid  @RequestBody CreateTrainerRequest createTrainerRequest) {
        return ResponseEntity.ok(trainerService.addTrainer(createTrainerRequest));
    }

    //FE-9
    @PutMapping("/{id}")
    public ResponseEntity<TrainerResponse> updateTrainer(
            @PathVariable Long id,
            @RequestBody UpdateTrainerRequest updateTrainerRequest) {
        return ResponseEntity.ok(trainerService.updateTrainer(id, updateTrainerRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TrainerResponse> deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
        return null;
    }


}

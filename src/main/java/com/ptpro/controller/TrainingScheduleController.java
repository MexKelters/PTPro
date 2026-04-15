package com.ptpro.controller;

import com.ptpro.dto.response.TrainingScheduleResponse;
import com.ptpro.model.TrainingSchedule;
import com.ptpro.service.TrainingScheduleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/training-schedules")
public class TrainingScheduleController {

    private final TrainingScheduleService trainingScheduleService;

    public TrainingScheduleController(TrainingScheduleService trainingScheduleService) {
        this.trainingScheduleService = trainingScheduleService;
    }

    // FE-18
    @PreAuthorize("hasRole('TRAINER')")
    @PostMapping("/upload/{trainerId}")
    public ResponseEntity<TrainingScheduleResponse> uploadSchedule(@PathVariable Long trainerId,
                                                           @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(trainingScheduleService.uploadSchedule(trainerId, file));
    }

    // FE-19
    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping("/{scheduleId}/assign/{userId}")
    public ResponseEntity<TrainingScheduleResponse> assignToUser(@PathVariable Long scheduleId,
                                                         @PathVariable Long userId) {
        return ResponseEntity.ok(trainingScheduleService.assignToUser(scheduleId, userId));
    }

    // FE-20
    @PreAuthorize("hasAnyRole('USER', 'TRAINER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrainingScheduleResponse>> getSchedulesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(trainingScheduleService.getSchedulesByUser(userId));
    }

    // FE-20
    @PreAuthorize("hasAnyRole('USER', 'TRAINER')")
    @GetMapping("/download/{scheduleId}")
    public ResponseEntity<byte[]> downloadSchedule(@PathVariable Long scheduleId) {
        TrainingSchedule schedule = trainingScheduleService.getScheduleById(scheduleId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + schedule.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(schedule.getContents());
    }

    // FE-21
    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<List<TrainingScheduleResponse>> getSchedulesByTrainer(@PathVariable Long trainerId) {
        return ResponseEntity.ok(trainingScheduleService.getSchedulesByTrainer(trainerId));
    }

    // FE-21
    @PreAuthorize("hasRole('TRAINER')")
    @DeleteMapping("/{scheduleId}/trainer/{trainerId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId,
                                               @PathVariable Long trainerId) {
        trainingScheduleService.deleteSchedule(scheduleId, trainerId);
        return ResponseEntity.noContent().build();
    }
}
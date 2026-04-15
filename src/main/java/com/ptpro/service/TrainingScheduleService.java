package com.ptpro.service;

import com.ptpro.model.Trainer;
import com.ptpro.model.TrainingSchedule;
import com.ptpro.model.User;
import com.ptpro.repository.SessionRepository;
import com.ptpro.repository.TrainerRepository;
import com.ptpro.repository.TrainingScheduleRepository;
import com.ptpro.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TrainingScheduleService {

    private final TrainingScheduleRepository trainingScheduleRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public TrainingScheduleService(TrainingScheduleRepository trainingScheduleRepository,
                                   TrainerRepository trainerRepository,
                                   UserRepository userRepository,
                                   SessionRepository sessionRepository) {
        this.trainingScheduleRepository = trainingScheduleRepository;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public TrainingSchedule uploadSchedule(Long trainerId, MultipartFile file) throws IOException {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer niet gevonden met id: " + trainerId));

        TrainingSchedule schedule = new TrainingSchedule();
        schedule.setTrainer(trainer);
        schedule.setFileName(file.getOriginalFilename());
        schedule.setContents(file.getBytes());

        return trainingScheduleRepository.save(schedule);
    }

    @Transactional
    public TrainingSchedule assignToUser(Long scheduleId, Long userId) {
        TrainingSchedule schedule = trainingScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schema niet gevonden met id: " + scheduleId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User niet gevonden met id: " + userId));

        schedule.setUser(user);
        return trainingScheduleRepository.save(schedule);
    }

    public List<TrainingSchedule> getSchedulesByUser(Long userId) {
        List<TrainingSchedule> schedules = trainingScheduleRepository.findAllByUserId(userId);
        if (schedules.isEmpty()) {
            throw new RuntimeException("Geen schema's gevonden voor user met id: " + userId);
        }
        return schedules;
    }


    public TrainingSchedule getScheduleById(Long scheduleId) {
        return trainingScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schema niet gevonden met id: " + scheduleId));
    }


    public List<TrainingSchedule> getSchedulesByTrainer(Long trainerId) {
        List<TrainingSchedule> schedules = trainingScheduleRepository.findAllByTrainerId(trainerId);
        if (schedules.isEmpty()) {
            throw new RuntimeException("Geen schema's gevonden voor trainer met id: " + trainerId);
        }
        return schedules;
    }

    @Transactional
    public void deleteSchedule(Long scheduleId, Long trainerId) {
        TrainingSchedule schedule = trainingScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schema niet gevonden met id: " + scheduleId));

        if (!schedule.getTrainer().getId().equals(trainerId)) {
            throw new RuntimeException("Je hebt geen toegang om dit schema te verwijderen");
        }

        trainingScheduleRepository.delete(schedule);
    }
}
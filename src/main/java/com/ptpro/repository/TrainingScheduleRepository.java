package com.ptpro.repository;

import com.ptpro.model.TrainingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingScheduleRepository extends JpaRepository<TrainingSchedule, Long> {
    List<TrainingSchedule> findAllByUserId(Long userId);
    List<TrainingSchedule> findAllByTrainerId(Long trainerId);
}
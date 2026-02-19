package com.ptpro.repository;

import com.ptpro.model.TrainingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingScheduleRepository extends JpaRepository<TrainingSchedule, Long> {
}

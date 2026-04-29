package com.ptpro.repository;

import com.ptpro.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByTrainerId(Long trainerId);

    @Query(value = "SELECT * FROM sessions WHERE trainer_id = :trainerId AND available = true", nativeQuery = true)
    List<Session> getAllAvailable(Long trainerId);
}

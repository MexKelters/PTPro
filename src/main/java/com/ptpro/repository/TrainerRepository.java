package com.ptpro.repository;

import com.ptpro.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    boolean existsByUserId(Long id);
}

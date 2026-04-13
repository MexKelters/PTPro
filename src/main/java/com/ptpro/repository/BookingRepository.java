package com.ptpro.repository;

import com.ptpro.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b JOIN b.users u WHERE u.id = :userId")
    List<Booking> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT b FROM Booking b JOIN b.session s JOIN s.trainer t WHERE t.id = :trainerId")
    List<Booking> findAllByTrainerId(@Param("trainerId") Long trainerId);
}
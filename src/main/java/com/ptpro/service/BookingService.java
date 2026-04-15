package com.ptpro.service;

import com.ptpro.model.Booking;
import com.ptpro.model.Session;
import com.ptpro.model.User;
import com.ptpro.repository.BookingRepository;
import com.ptpro.repository.SessionRepository;
import com.ptpro.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SessionService sessionService;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, SessionService sessionService, SessionRepository sessionRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.sessionService = sessionService;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public List<Booking> getBookingsByUser(Long userId) {
        List<Booking> bookings = bookingRepository.findAllByUserId(userId);
        if (bookings.isEmpty()) {
            throw new RuntimeException("Geen bookings gevonden voor user met id: " + userId);
        }
        return bookings;
    }

    public List<Booking> getBookingsByTrainer(Long trainerId) {
        List<Booking> bookings = bookingRepository.findAllByTrainerId(trainerId);
        if (bookings.isEmpty()) {
            throw new RuntimeException("Geen bookings gevonden voor trainer met id: " + trainerId);
        }
        return bookings;
    }

    @Transactional
    public Booking createBooking(Long sessionId, Long userId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Sessie niet gevonden met id: " + sessionId));

        if (!session.isAvailable()) {
            throw new RuntimeException("Sessie is niet meer beschikbaar");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User niet gevonden met id: " + userId));

        session.setAvailable(false);
        sessionRepository.save(session);
        Booking booking = new Booking();
        booking.setSession(session);
        booking.setStatus(true);
        booking.setDate(LocalDateTime.now());

        List<User> users = new ArrayList<>();
        users.add(user);
        booking.setUsers(users);

        return bookingRepository.save(booking);
    }


    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking niet gevonden met id: " + bookingId));

        Session session = booking.getSession();
        session.setAvailable(true);
        sessionRepository.save(session);

        bookingRepository.delete(booking);
    }
}
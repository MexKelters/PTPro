package com.ptpro.service;

import com.ptpro.dto.response.BookingResponse;
import com.ptpro.exception.ResourceNotFoundException;
import com.ptpro.mapper.BookingMapper;
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
    private final BookingMapper bookingMapper;

    public BookingService(BookingRepository bookingRepository,
                          SessionService sessionService,
                          SessionRepository sessionRepository,
                          UserRepository userRepository,
                          BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.sessionService = sessionService;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.bookingMapper = bookingMapper;
    }

    public List<BookingResponse> getBookingsByUser(Long userId) {
        List<Booking> bookings = bookingRepository.findAllByUserId(userId);
        if (bookings.isEmpty()) {
            throw new RuntimeException("Geen bookings gevonden voor user met id: " + userId);
        }
        List<BookingResponse> dtos = new ArrayList<>();
        for (Booking booking : bookings) {
            dtos.add(bookingMapper.toResponse(booking));
        }
        return dtos;
    }

    public List<BookingResponse> getBookingsByTrainer(Long trainerId) {
        List<Booking> bookings = bookingRepository.findAllByTrainerId(trainerId);
        if (bookings.isEmpty()) {
            throw new RuntimeException("Geen bookings gevonden voor trainer met id: " + trainerId);
        }
        List<BookingResponse> dtos = new ArrayList<>();
        for (Booking booking : bookings) {
            dtos.add(bookingMapper.toResponse(booking));
        }
        return dtos;
    }

    @Transactional
    public BookingResponse createBooking(Long sessionId, Long userId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Sessie niet gevonden met id: " + sessionId));

        if (!session.isAvailable()) {
            throw new ResourceNotFoundException("Sessie is niet meer beschikbaar");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User niet gevonden met id: " + userId));

        session.setAvailable(false);
        sessionRepository.save(session);

        Booking booking = new Booking();
        booking.setSession(session);
        booking.setStatus(true);
        booking.setDate(LocalDateTime.now());

        List<User> users = new ArrayList<>();
        users.add(user);
        booking.setUsers(users);

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toResponse(savedBooking);
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking niet gevonden met id: " + bookingId));

        Session session = booking.getSession();
        session.setAvailable(true);
        sessionRepository.save(session);

        bookingRepository.delete(booking);
    }
}
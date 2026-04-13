package com.ptpro.controller;

import com.ptpro.model.Booking;
import com.ptpro.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/create/{sessionId}/{userId}")
    public ResponseEntity<Booking> createBooking(@PathVariable Long sessionId,
                                                 @PathVariable Long userId) {
        Booking booking = bookingService.createBooking(sessionId, userId);
        return ResponseEntity.ok(booking);
    }
}
package com.ptpro.controller;

import com.ptpro.dto.response.BookingResponse;
import com.ptpro.model.Booking;
import com.ptpro.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //FE-16
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(@PathVariable Long userId) {
        List<BookingResponse> bookings = bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(bookings);
    }

    //FE-14
    //FE-15
    @PostMapping("/create/{sessionId}/{userId}")
    public ResponseEntity<BookingResponse> createBooking(@PathVariable Long sessionId,
                                                 @PathVariable Long userId) {
        BookingResponse booking = bookingService.createBooking(sessionId, userId);
        return ResponseEntity.ok(booking);
    }

    //FE-27
    //FE-17
    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
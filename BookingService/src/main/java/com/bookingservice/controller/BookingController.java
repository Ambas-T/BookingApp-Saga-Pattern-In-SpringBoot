package com.bookingservice.controller;

import com.bookingservice.dto.BookingDto;
import com.bookingservice.exception.BookingException;
import com.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto bookingDto) throws BookingException {
        BookingDto newBooking = bookingService.createBooking(bookingDto);
        return ResponseEntity.ok(newBooking);
    }
}

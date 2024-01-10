package com.booking.app.flightservice.controller;

import com.booking.app.flightservice.Exception.FlightException;
import com.booking.app.flightservice.dto.FlightBookingDto;
import com.booking.app.flightservice.dto.FlightBookingRequestDto;
import com.booking.app.flightservice.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    // Create a new flight booking
    @PostMapping("/book")
    public ResponseEntity<FlightBookingDto> bookFlight(@RequestBody FlightBookingRequestDto booking) throws FlightException {
        FlightBookingDto response = flightService.bookFlight(booking);
        return ResponseEntity.ok(response);
    }

    // Cancel a flight booking
    @DeleteMapping("/cancel/{flightBookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable("flightBookingId") Long flightBookingId) throws FlightException {
        flightService.cancelBooking(flightBookingId);
        return ResponseEntity.ok("Booking cancelled successfully.");
    }
}


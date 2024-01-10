package com.booking.app.flightservice.controller;

import com.example.flightservice.Exception.FlightException;
import com.example.flightservice.dto.FlightBookingDto;
import com.example.flightservice.dto.FlightBookingRequestDto;
import com.example.flightservice.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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


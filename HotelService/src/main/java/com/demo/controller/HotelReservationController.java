package com.demo.controller;

import com.example.hotelservice.Exception.HotelException;
import com.example.hotelservice.dto.HotelBookingRequest;
import com.example.hotelservice.dto.HotelReservationDto;
import com.example.hotelservice.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelReservationController {

    @Autowired
    private HotelService hotelService;

    // Create a hotel reservation
    @PostMapping("/reservations")
    public ResponseEntity<HotelReservationDto> createReservation(@Valid @RequestBody HotelBookingRequest request) throws HotelException {
        HotelReservationDto response = hotelService.createReservation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Delete a reservation
    @DeleteMapping("/cancel/{hotelBookingId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("hotelBookingId") Long hotelBookingId) throws HotelException {
        hotelService.deleteReservation(hotelBookingId);
        return ResponseEntity.noContent().build();
    }
}


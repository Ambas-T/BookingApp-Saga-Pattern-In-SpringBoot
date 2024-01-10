package com.demo.controller;


import com.demo.Exception.HotelException;
import com.demo.dto.HotelBookingRequest;
import com.demo.dto.HotelReservationDto;
import com.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


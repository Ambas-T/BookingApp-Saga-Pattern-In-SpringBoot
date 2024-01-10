package com.booking.app.carservice.controller;

import com.booking.app.carservice.Exception.CarException;
import com.booking.app.carservice.dto.CarBookingDto;
import com.booking.app.carservice.dto.CarBookingRequestDto;
import com.booking.app.carservice.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/cars")
public class CarBookingController {

    @Autowired
    private CarService carService;

    // Create a car booking
    @PostMapping("/bookings")
    public ResponseEntity<CarBookingDto> createCarBooking(@Valid @RequestBody CarBookingRequestDto carBookingDto) throws CarException {
        log.info("Request to create a car booking: {}", carBookingDto);
        CarBookingDto responseDto = carService.createCarBooking(carBookingDto);
        log.info("Car booking created successfully: {}", responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Delete a car booking
    @DeleteMapping("/cancel/{carBookingId}")
    public ResponseEntity<Void> deleteCarBooking(@PathVariable("carBookingId") Long carBookingId) throws CarException {
        carService.deleteCarBooking(carBookingId);
        return ResponseEntity.noContent().build();
    }

}

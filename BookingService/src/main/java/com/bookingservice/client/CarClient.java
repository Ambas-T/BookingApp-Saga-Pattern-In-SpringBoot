package com.bookingservice.client;

import com.bookingservice.dto.CarBookingResponse;
import com.bookingservice.dto.CarBookingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "carService", url = "http://localhost:8081/api/v1/cars")
public interface CarClient {

    @PostMapping("/bookings")
    CarBookingResponse bookCar(@RequestBody CarBookingRequest request);

    @DeleteMapping("/cancel/{carBookingId}")
    void cancelCarBooking(@PathVariable("carBookingId") Long carBookingId);
}

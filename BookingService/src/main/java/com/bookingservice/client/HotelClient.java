package com.bookingservice.client;

import com.bookingservice.dto.HotelBookingRequest;
import com.bookingservice.dto.HotelBookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hotelService", url = "http://localhost:8082/api/v1/hotels") // Adjust the URL
public interface HotelClient {

    @PostMapping("/reservations")
    HotelBookingResponse bookHotel(@RequestBody HotelBookingRequest request);

    @DeleteMapping("/cancel/{hotelBookingId}")
    void cancelHotelBooking(@PathVariable("hotelBookingId") Long hotelBookingId);
}

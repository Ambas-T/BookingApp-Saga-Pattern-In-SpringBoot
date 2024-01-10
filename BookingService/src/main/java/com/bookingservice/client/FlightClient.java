package com.bookingservice.client;

import com.bookingservice.dto.FlightBookingRequest;
import com.bookingservice.dto.FlightBookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "flightService", url = "http://localhost:8083/api/v1/flights") // Adjust the URL
public interface FlightClient {

    @PostMapping("/book")
    FlightBookingResponse bookFlight(@RequestBody FlightBookingRequest request);

    @DeleteMapping("/cancel/{flightBookingId}")
    void cancelFlightBooking(@PathVariable("flightBookingId") Long flightBookingId);
}

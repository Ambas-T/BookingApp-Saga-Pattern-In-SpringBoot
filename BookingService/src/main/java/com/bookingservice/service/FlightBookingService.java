package com.bookingservice.service;

import com.bookingservice.dto.FlightBookingResponse;
import com.bookingservice.client.FlightClient;
import com.bookingservice.dto.FlightBookingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FlightBookingService {

    private final FlightClient flightClient;

    public Long bookFlight(Long id, String flightDetails) {
        try {
            FlightBookingRequest request = new FlightBookingRequest(id, flightDetails);
            FlightBookingResponse response = flightClient.bookFlight(request);

            if (response.isSuccess()) {
                log.info("Flight booked successfully with booking ID: {}", response.getBookingId());
                return response.getBookingId();
            } else {
                log.error("Flight booking failed for ID: {}, details: {}", id, flightDetails);
                throw new RuntimeException("Flight booking failed");
            }
        } catch (Exception e) {
            log.error("Error booking flight for ID: {}, details: {}, error: {}", id, flightDetails, e.getMessage(), e);
            throw new RuntimeException("Error booking flight: " + e.getMessage(), e);
        }
    }

    public void compensateFlightBooking(Long flightBookingId) {
        try {
            flightClient.cancelFlightBooking(flightBookingId);
            log.info("Flight booking compensation successful for booking ID: {}", flightBookingId);
        } catch (Exception e) {
            log.error("Failed to compensate flight booking for booking ID: {}, error: {}", flightBookingId, e.getMessage(), e);
            throw new RuntimeException("Failed to compensate flight booking: " + e.getMessage(), e);
        }
    }
}


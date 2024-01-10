package com.bookingservice.service;

import com.bookingservice.client.HotelClient;
import com.bookingservice.dto.HotelBookingRequest;
import com.bookingservice.dto.HotelBookingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Service
public class HotelBookingService {

    private final HotelClient hotelClient;

    public Long bookHotel(Long id, String hotelDetails) {
        LocalDate today = LocalDate.now();
        try {
            HotelBookingRequest request = new HotelBookingRequest(id, hotelDetails, today, today);
            HotelBookingResponse response = hotelClient.bookHotel(request);

            if (response.isSuccess()) {
                log.info("Hotel booked successfully with booking ID: {}", response.getBookingId());
                return response.getBookingId();
            } else {
                log.error("Hotel booking failed for ID: {}, details: {}", id, hotelDetails);
                throw new RuntimeException("Hotel booking failed");
            }
        } catch (Exception e) {
            log.error("Error booking hotel for ID: {}, details: {}, error: {}", id, hotelDetails, e.getMessage(), e);
            throw new RuntimeException("Error booking hotel: " + e.getMessage(), e);
        }
    }

    public void compensateHotelBooking(Long hotelBookingId) {
        try {
            hotelClient.cancelHotelBooking(hotelBookingId);
            log.info("Hotel booking compensation successful for booking ID: {}", hotelBookingId);
        } catch (Exception e) {
            log.error("Failed to compensate hotel booking for booking ID: {}, error: {}", hotelBookingId, e.getMessage(), e);
            throw new RuntimeException("Failed to compensate hotel booking: " + e.getMessage(), e);
        }
    }
}

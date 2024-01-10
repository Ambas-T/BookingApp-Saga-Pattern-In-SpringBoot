package com.bookingservice.Service;

import com.bookingservice.DTO.CarBookingResponse;
import com.bookingservice.Client.CarClient;
import com.bookingservice.DTO.CarBookingRequest;
import com.bookingservice.Exception.BookingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class CarBookingService {

    @Autowired
    private CarClient carClient;

    public Long bookCar(Long bookingId, String carDetails, LocalDate startDate, LocalDate endDate) throws BookingException {
        try {
            CarBookingRequest request = new CarBookingRequest(bookingId, carDetails, startDate, endDate);
            log.info("Car booked successfully with booking ID: {} CarBookingRequest -> {}", bookingId, request);

            CarBookingResponse response = carClient.bookCar(request);

            if (response.isSuccess()) {
                log.info("Car booked successfully with booking ID: {}", response.getBookingId());
                return response.getBookingId();
            } else {
                log.error("Car booking failed for booking ID: {}", bookingId);
                throw new BookingException("Car booking failed");
            }
        } catch (Exception e) {
            log.error("Error during car booking for booking ID: {}, error: {}", bookingId, e.getMessage(), e);
            throw new BookingException("Car booking failed: " + e.getMessage(), e);
        }
    }

    public void compensateCarBooking(Long carBookingId) throws BookingException {
        try {
            carClient.cancelCarBooking(carBookingId);
            log.info("Car booking compensation successful for booking ID: {}", carBookingId);
        } catch (Exception e) {
            log.error("Failed to compensate car booking for booking ID: {}, error: {}", carBookingId, e.getMessage(), e);
            throw new BookingException("Failed to compensate car booking: " + e.getMessage(), e);
        }
    }
}

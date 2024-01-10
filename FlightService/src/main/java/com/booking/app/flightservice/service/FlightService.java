package com.booking.app.flightservice.service;

import com.booking.app.flightservice.Exception.FlightException;
import com.booking.app.flightservice.dto.FlightBookingDto;
import com.booking.app.flightservice.dto.FlightBookingRequestDto;
import com.booking.app.flightservice.entity.FlightBooking;
import com.booking.app.flightservice.repository.FlightBookingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class FlightService {

    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @Transactional
    public FlightBookingDto bookFlight(FlightBookingRequestDto booking) throws FlightException {
        try {
            log.info("Initiating flight booking with details: {}", booking);

            FlightBookingDto response = new FlightBookingDto();

            FlightBooking flightBooking = new FlightBooking();
            flightBooking.setFlightNumber(booking.getFlightType());
            flightBooking.setArrivalTime(booking.getEndDate());
            flightBooking.setId(booking.getUserId());
            flightBooking.setDepartureTime(booking.getStartDate());

            flightBookingRepository.save(flightBooking);

            response.setSuccess(true);
            response.setBookingId(booking.getUserId());

            log.info("Flight booking completed successfully for user ID: {}", booking.getUserId());

            return response;
        } catch (Exception e) {
            log.error("Error occurred during flight booking: {}", e.getMessage(), e);
            throw new FlightException("Error booking flight", e);
        }
    }

    @Transactional
    public void cancelBooking(Long id) throws FlightException {
        try {
            if (!flightBookingRepository.existsById(id)) {
                log.error("Attempted to cancel a flight booking that does not exist with ID: {}", id);
                throw new EntityNotFoundException("Flight booking not found with ID: " + id);
            }

            flightBookingRepository.deleteById(id);
            log.info("Flight booking cancelled successfully for ID: {}", id);
        } catch (EmptyResultDataAccessException e) {
            log.error("No flight booking found to delete with ID: {}", id, e);
            throw new FlightException("Error: No flight booking found, ID -> " + id, e);
        } catch (Exception e) {
            log.error("Error occurred while cancelling flight booking with ID: {}: {}", id, e.getMessage(), e);
            throw new FlightException("Error cancelling flight booking with ID: " + id, e);
        }
    }

}


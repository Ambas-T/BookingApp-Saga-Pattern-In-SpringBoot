package com.demo.service;

import com.example.hotelservice.Exception.HotelException;
import com.example.hotelservice.dto.HotelBookingRequest;
import com.example.hotelservice.dto.HotelReservationDto;
import com.example.hotelservice.entity.HotelReservation;
import com.example.hotelservice.repository.HotelReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class HotelService {

    @Autowired
    private HotelReservationRepository hotelReservationRepository;

    @Transactional
    public HotelReservationDto createReservation(HotelBookingRequest request) throws HotelException {
        try {
            log.info("Creating hotel reservation for user ID: {}", request.getUserId());

            HotelReservation reservation = new HotelReservation();
            reservation.setHotelName(request.getHotelName());
            reservation.setCheckInDate(request.getCheckInDate());
            reservation.setCheckOutDate(request.getCheckOutDate());
            reservation.setUserId(request.getUserId());

            HotelReservation savedReservation = hotelReservationRepository.save(reservation);

            HotelReservationDto response = new HotelReservationDto();
            response.setSuccess(true);
            response.setBookingId(request.getUserId());

            log.info("Hotel reservation created successfully for user ID: {}", request.getUserId());
            return response;
        } catch (Exception e) {
            log.error("Error occurred while creating hotel reservation for user ID: {}: {}", request.getUserId(), e.getMessage(), e);
            throw new HotelException("Error creating hotel reservation for user ID: " + request.getUserId(), e);
        }
    }

    @Transactional
    public void deleteReservation(Long id) throws HotelException {
        try {
            String reservationId = String.valueOf(id);
            if (!hotelReservationRepository.existsById(reservationId)) {
                log.error("Attempted to delete a hotel reservation that does not exist with ID: {}", reservationId);
                throw new EntityNotFoundException("Hotel reservation not found with ID: " + reservationId);
            }

            hotelReservationRepository.deleteById(reservationId);
            log.info("Hotel reservation successfully deleted for ID: {}", reservationId);
        } catch (EmptyResultDataAccessException e) {
            log.error("No hotel reservation found to delete with ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while deleting hotel reservation with ID: {}: {}", id, e.getMessage(), e);
            throw new HotelException("Error deleting hotel reservation with ID: " + id, e);
        }
    }
}

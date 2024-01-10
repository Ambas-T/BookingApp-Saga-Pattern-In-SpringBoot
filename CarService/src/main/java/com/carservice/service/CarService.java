package com.carservice.service;

import com.carservice.Exception.CarException;
import com.carservice.dto.CarBookingDto;
import com.carservice.dto.CarBookingRequestDto;
import com.carservice.entity.CarBooking;
import com.carservice.repository.CarBookingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CarService {

    @Autowired
    private CarBookingRepository carBookingRepository;

    @Transactional
    public CarBookingDto createCarBooking(CarBookingRequestDto carBookingDto) throws CarException {
        try {
            log.info("Creating car booking with details: {}", carBookingDto);
            CarBooking newCarBooking = new CarBooking();

            mapDtoToEntity(carBookingDto, newCarBooking);

            CarBooking savedBooking = carBookingRepository.save(newCarBooking);
            CarBookingDto responseDto = mapToDto(savedBooking);

            log.info("Car booking created successfully with ID: {}", responseDto.getBookingId());
            return responseDto;
        } catch (Exception e) {
            log.error("Failed to create car booking: {}", e.getMessage(), e);
            throw new CarException("Error creating car booking", e);
        }
    }

    public void deleteCarBooking(Long id) throws CarException {
        try {
            if (!carBookingRepository.existsById(id)) {
                log.error("Attempted to delete a car booking that does not exist with id: {}", id);
                throw new EntityNotFoundException("Car booking not found with id: " + id);
            }
            carBookingRepository.deleteById(id);
            log.info("Car booking successfully deleted with id: {}", id);
        } catch (Exception e) {
            log.error("Error occurred while deleting car booking with id: {}, error: {}", id, e.getMessage(), e);
            throw new CarException("Error deleting car booking with id: " + id, e);
        }
    }

    private CarBookingDto mapToDto(CarBooking carBooking) {
        CarBookingDto response = new CarBookingDto();
        response.setBookingId(carBooking.getId());
        response.setSuccess(true);
        return response;
    }

    private void mapDtoToEntity(CarBookingRequestDto dto, CarBooking entity) {
        entity.setUserId(dto.getUserId());
        entity.setCarType(dto.getCarType());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
    }
}

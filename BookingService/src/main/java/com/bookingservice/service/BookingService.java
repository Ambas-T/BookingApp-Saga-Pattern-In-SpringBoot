package com.bookingservice.service;

import com.bookingservice.dto.BookingDto;
import com.bookingservice.repository.BookingRepository;
import com.bookingservice.entity.Booking;
import com.bookingservice.exception.BookingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CarBookingService carBookingService;
    private final HotelBookingService hotelBookingService;
    private final FlightBookingService flightBookingService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, CarBookingService carBookingService, HotelBookingService hotelBookingService, FlightBookingService flightBookingService) {
        this.bookingRepository = bookingRepository;
        this.carBookingService = carBookingService;
        this.hotelBookingService = hotelBookingService;
        this.flightBookingService = flightBookingService;
    }

    @Transactional
    public BookingDto createBooking(BookingDto bookingDto) throws BookingException {
        log.info("Creating a new booking");

        // Start the Saga
        Booking booking = new Booking();
        booking.setStatus("PENDING");
        booking.setSagaState("STARTED");
        booking = bookingRepository.save(booking);
        log.debug("Booking saved with PENDING status and STARTED saga state");

        try {
            Long carBookingId = carBookingService.bookCar(booking.getId(), bookingDto.getCarDetails(), bookingDto.getStartDate(), bookingDto.getEndDate());
            booking.setCarBookingId(carBookingId);
            log.debug("Car booked with ID: {}", carBookingId);

            Long hotelBookingId = hotelBookingService.bookHotel(booking.getId(), bookingDto.getHotelDetails());
            booking.setHotelBookingId(hotelBookingId);
            log.debug("Hotel booked with ID: {}", hotelBookingId);

            Long flightBookingId = flightBookingService.bookFlight(booking.getId(), bookingDto.getFlightDetails());
            booking.setFlightBookingId(flightBookingId);
            log.debug("Flight booked with ID: {}", flightBookingId);

            booking.setStatus("CONFIRMED");
            booking.setSagaState("COMPLETED");
            log.info("Booking confirmed and saga completed");

        } catch (Exception e) {
            log.error("Error occurred during booking, starting compensating transactions", e);
            // Compensating Transactions
            compensateBookings(booking);
            throw new BookingException("Error during booking process");
        }
        return convertToDto(bookingRepository.save(booking));
    }

    private void compensateBookings(Booking booking) throws BookingException {
        log.info("Compensating bookings for booking ID: {}", booking.getId());

        // Compensate Car Booking
        if (booking.getCarBookingId() != null) {
            log.debug("Compensating car booking with ID: {}", booking.getCarBookingId());
            carBookingService.compensateCarBooking(booking.getCarBookingId());
        } else {
            log.debug("No car booking to compensate for booking ID: {}", booking.getId());
        }

        // Compensate Hotel Booking
        if (booking.getHotelBookingId() != null) {
            log.debug("Compensating hotel booking with ID: {}", booking.getHotelBookingId());
            hotelBookingService.compensateHotelBooking(booking.getHotelBookingId());
        } else {
            log.debug("No hotel booking to compensate for booking ID: {}", booking.getId());
        }

        // Compensate Flight Booking
        if (booking.getFlightBookingId() != null) {
            log.debug("Compensating flight booking with ID: {}", booking.getFlightBookingId());
            flightBookingService.compensateFlightBooking(booking.getFlightBookingId());
        } else {
            log.debug("No flight booking to compensate for booking ID: {}", booking.getId());
        }

        // Update Booking Status
        booking.setStatus("CANCELLED");
        booking.setSagaState("COMPENSATED");
        log.info("Booking status updated to CANCELLED and saga state to COMPENSATED for booking ID: {}", booking.getId());
    }


    // Conversion method from Booking entity to BookingDto
    private BookingDto convertToDto(Booking booking) {
        BookingDto response = new BookingDto();
        response.setCarDetails(String.valueOf(booking.getCarBookingId()));
        response.setFlightDetails(String.valueOf(booking.getFlightBookingId()));
        response.setHotelDetails(String.valueOf(booking.getHotelBookingId()));
        response.setStartDate(LocalDate.now());
        response.setEndDate(LocalDate.now());
        return response;
    }
}

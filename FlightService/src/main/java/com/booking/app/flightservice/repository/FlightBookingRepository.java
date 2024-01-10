package com.booking.app.flightservice.repository;


import com.booking.app.flightservice.entity.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {

}


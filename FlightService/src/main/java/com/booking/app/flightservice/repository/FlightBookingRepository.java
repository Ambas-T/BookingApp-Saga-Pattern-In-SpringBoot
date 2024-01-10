package com.booking.app.flightservice.repository;

import com.example.flightservice.entity.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {

}


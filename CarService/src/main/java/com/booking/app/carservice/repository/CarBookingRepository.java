package com.booking.app.carservice.repository;

import com.booking.app.carservice.entity.CarBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarBookingRepository extends JpaRepository<CarBooking, Long> {

}


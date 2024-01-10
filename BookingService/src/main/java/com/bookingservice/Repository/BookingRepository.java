package com.bookingservice.Repository;


import com.bookingservice.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}

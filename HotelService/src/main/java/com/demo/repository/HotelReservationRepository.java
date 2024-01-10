package com.demo.repository;

import com.demo.entity.HotelReservation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotelReservationRepository extends MongoRepository<HotelReservation, String> {
    // Custom query methods if needed
}


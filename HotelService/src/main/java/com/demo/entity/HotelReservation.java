package com.demo.entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelReservation {
    @Id
    private String id;
    private Long userId;
    private String hotelName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}

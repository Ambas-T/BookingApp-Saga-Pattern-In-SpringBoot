package com.bookingservice.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class BookingDto {
    private String carDetails;
    private String hotelDetails;
    private String flightDetails;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;
}

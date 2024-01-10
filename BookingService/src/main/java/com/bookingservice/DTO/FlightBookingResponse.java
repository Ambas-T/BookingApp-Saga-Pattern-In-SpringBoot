package com.bookingservice.DTO;

import lombok.Data;

@Data
public class FlightBookingResponse {
    private boolean success;
    private Long BookingId;
}

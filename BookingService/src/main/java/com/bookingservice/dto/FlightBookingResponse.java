package com.bookingservice.dto;

import lombok.Data;

@Data
public class FlightBookingResponse {
    private boolean success;
    private Long BookingId;
}

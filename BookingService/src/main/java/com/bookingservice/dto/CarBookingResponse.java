package com.bookingservice.dto;

import lombok.Data;

@Data
public class CarBookingResponse {
    private boolean success;
    private Long bookingId;
}

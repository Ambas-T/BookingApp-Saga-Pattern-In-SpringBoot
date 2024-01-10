package com.bookingservice.dto;

import lombok.Data;

@Data
public class HotelBookingResponse {
    private boolean success;
    private Long bookingId;
}

package com.bookingservice.exception;

public class BookingException extends Exception {

    public BookingException(String ex) {
        super(ex);
    }

    public BookingException(String ex, Exception e) {
        super(ex, e);
    }
}

package com.demo.Exception;

public class HotelException extends Exception {

    public HotelException(String ex) {
        super(ex);
    }

    public HotelException(String ex, Exception e) {
        super(ex, e);
    }
}

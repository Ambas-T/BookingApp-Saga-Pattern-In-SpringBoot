package com.flightservice.Exception;

public class FlightException extends Exception {

    public FlightException(String ex) {
        super(ex);
    }

    public FlightException(String ex, Exception e) {
        super(ex, e);
    }
}

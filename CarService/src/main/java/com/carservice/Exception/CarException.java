package com.carservice.Exception;

public class CarException extends Exception {

    public CarException(String ex) {
        super(ex);
    }

    public CarException(String ex, Exception e) {
        super(ex, e);
    }
}

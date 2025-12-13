package com.secureInsurance.customerServices.exception;

public class CustomerAlreadyExitsException extends RuntimeException {
    public CustomerAlreadyExitsException(String message) {
        super(message);
    }
}


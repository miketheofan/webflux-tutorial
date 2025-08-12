package com.example.webflux_tutorial.sec04.exceptions;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}

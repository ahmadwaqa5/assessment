package com.waqasassessment.orderservice.exception;

import lombok.Data;

import java.time.Instant;

@Data
public class ApiError {

    private int code;
    private String message;
    private Instant timestamp;

    public ApiError(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public ApiError(int code, String message, Instant timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters and setters here...
}

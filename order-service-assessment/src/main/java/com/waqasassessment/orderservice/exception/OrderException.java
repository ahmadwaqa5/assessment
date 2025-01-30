package com.waqasassessment.orderservice.exception;


import lombok.Data;

@Data
public class OrderException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ErrorType errorType;
    public OrderException(final ErrorType errorType, final String endUserMessage) {
        super(endUserMessage);
        this.errorType = errorType;
    }

}

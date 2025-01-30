package com.waqasassessment.inventoryservice.exception;


import lombok.Data;

@Data
public class InventoryException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ErrorType errorType;
    public InventoryException(final ErrorType errorType, final String endUserMessage) {
        super(endUserMessage);
        this.errorType = errorType;
    }

}

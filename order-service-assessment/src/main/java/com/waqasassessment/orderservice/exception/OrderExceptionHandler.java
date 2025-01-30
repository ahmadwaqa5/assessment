package com.waqasassessment.orderservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class OrderExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = OrderException.class)
    protected ResponseEntity<Object> handleResourceException(final OrderException ex) {

        final int httpCode = ex.getErrorType().getRecommendedHttpCode();
        final HttpStatus httpStatus = HttpStatus.valueOf(httpCode);
        final String endUserMessage = ex.getMessage();

        final ApiError apiError = new ApiError(httpCode, endUserMessage);
        return new ResponseEntity<>(apiError, httpStatus);
    }
}

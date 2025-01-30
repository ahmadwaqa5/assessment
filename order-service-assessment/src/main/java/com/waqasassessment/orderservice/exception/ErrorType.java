package com.waqasassessment.orderservice.exception;

public enum ErrorType  {
    VALIDATION_FAILURE(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    BAD_CSRF_TOKEN(412),
    DUPLICATE_VALUE(422),
    UNPROCESSABLE_ENTITY(422),
    UPDATED_ENTITY_MISSING(405),
    UNEXPECTED(500);

    private final int recommendedHttpCode;

    private ErrorType(int recommendedHttpCode) {
        this.recommendedHttpCode = recommendedHttpCode;
    }

    public int getRecommendedHttpCode() {
        return this.recommendedHttpCode;
    }
}

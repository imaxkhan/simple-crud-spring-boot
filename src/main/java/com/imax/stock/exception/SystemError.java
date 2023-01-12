package com.imax.stock.exception;

import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
public enum SystemError {
    SERVER_ERROR(HttpServletResponse.SC_SERVICE_UNAVAILABLE),
    ILLEGAL_ARGUMENT(HttpServletResponse.SC_EXPECTATION_FAILED),
    DATA_NOT_FOUND(HttpServletResponse.SC_NOT_FOUND),
    VALIDATION_EXCEPTION(HttpServletResponse.SC_BAD_REQUEST),
    UPLOAD_SIZE_EXCEEDED(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);

    private final Integer value;

    SystemError(Integer value) {
        this.value = value;
    }
}

package com.imax.stock.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class CustomException extends Exception {
    private final SystemError error;
    private final Integer errorCode;
    private final Object argument;
    private final List<ErrorResult> errorResults;

    public CustomException(SystemError error, Object argument, Integer errorCode) {
        this.error = error;
        this.argument = argument;
        this.errorCode = errorCode;
        this.errorResults = null;
    }

    public CustomException(SystemError error, Object argument, Integer errorCode, List<ErrorResult> errorResults) {
        this.error = error;
        this.argument = argument;
        this.errorCode = errorCode;
        this.errorResults = errorResults;
    }
}

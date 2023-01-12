package com.imax.stock.exception;

import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
public class ErrorResult {
    private SystemError error;
    private Integer errorCode;
    private Object description;

    public ErrorResult(SystemError error, Integer errorCode, Object description) {
        this.error = error;
        this.errorCode = errorCode;
        this.description = description;
    }

    public ErrorResult(CustomException exception) {
        this.error = exception.getError();
        this.errorCode = exception.getErrorCode();
        this.description=exception.getArgument();
    }

    public ErrorResult(CustomException exception, HttpServletResponse response) {
        this.error = exception.getError();
        this.errorCode = exception.getErrorCode();
        response.setStatus(this.error.getValue());
    }


}

package com.imax.stock.exception;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @Value("${imax}")
    private String maxUpload;

    @ExceptionHandler(CustomException.class)
    public List<ErrorResult> handleSystemException(CustomException exception, HttpServletResponse response) {
        response.setStatus(exception.getError().getValue());
        if (exception.getErrorResults() != null) {
            return exception.getErrorResults();
        }
        return Collections.singletonList(new ErrorResult(exception));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResult constraintViolationException(ConstraintViolationException exception, HttpServletResponse response) {
        List<String> validationList = new ArrayList<>();

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String argument = "";
            if (violation.getPropertyPath() != null) {
                for (Path.Node node : violation.getPropertyPath()) {
                    argument = node.getName();
                }
                argument += " ";
            }
            argument += violation.getMessage();
            validationList.add(argument);
        }
        String fields = String.join(",", validationList);
        response.setStatus(SystemError.VALIDATION_EXCEPTION.getValue());
        return new ErrorResult(SystemError.VALIDATION_EXCEPTION, 1000, fields);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ErrorResult handleMultiPartFileSizeException(MaxUploadSizeExceededException e, HttpServletResponse response) {
        return new ErrorResult(SystemError.UPLOAD_SIZE_EXCEEDED, 500, "upload size is " + maxUpload);

    }

    @ExceptionHandler(Exception.class)
    public ErrorResult handleUnHandledException(Exception exception, HttpServletResponse response) {
        exception.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ErrorResult(SystemError.SERVER_ERROR, 9090, exception.getMessage());
    }
}

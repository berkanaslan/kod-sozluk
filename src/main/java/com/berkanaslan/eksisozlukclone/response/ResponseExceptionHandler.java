package com.berkanaslan.eksisozlukclone.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice()
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_MESSAGE = "Error!";

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        ResponseWrapper responseWrapper = new ResponseWrapper(ERROR_MESSAGE, ex.getMessage());
        return handleExceptionInternal(ex, responseWrapper, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
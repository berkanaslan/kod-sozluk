package com.berkanaslan.eksisozlukclone.response;

import com.berkanaslan.eksisozlukclone.util.ExceptionMessageUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice()
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        ResponseWrapper responseWrapper = new ResponseWrapper(ExceptionMessageUtil.getMessageByLocale("message.error"), ex.getMessage());
        return handleExceptionInternal(ex, responseWrapper, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
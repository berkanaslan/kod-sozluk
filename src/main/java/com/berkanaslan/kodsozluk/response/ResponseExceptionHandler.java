package com.berkanaslan.kodsozluk.response;

import com.berkanaslan.kodsozluk.util.I18NUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice()
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseExceptionHandler.class);

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        ResponseWrapper responseWrapper = new ResponseWrapper(I18NUtil.getMessageByLocale("message.error"), ex.getMessage());
        LOGGER.warn("Exception occur: ", ex);

        return handleExceptionInternal(ex, responseWrapper, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
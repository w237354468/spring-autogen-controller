package org.lcdpframework.web;

import org.lcdpframework.web.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Response<Void> handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return Response.fail();
    }
}

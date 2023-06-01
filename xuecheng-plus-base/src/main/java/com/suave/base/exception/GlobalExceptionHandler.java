package com.suave.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Suave
 * @since 2023/06/01 21:52
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(XCPException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(XCPException e) {
        return new RestErrorResponse(e.getErrMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse commonException(Exception e) {
        return new RestErrorResponse(CommonError.UNKNOWN_ERROR.getErrMessage());
    }
}

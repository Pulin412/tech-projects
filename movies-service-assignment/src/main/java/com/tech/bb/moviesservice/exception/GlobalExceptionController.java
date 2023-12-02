package com.tech.bb.moviesservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage movieNotFoundException(MovieNotFoundException ex, WebRequest request) {

        return new ErrorMessage(
                LocalDateTime.now(),
                ex.getMessage());
    }

    @ExceptionHandler(UserAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage userNotAuthenticatedException(UserAuthenticationException ex, WebRequest request) {

        return new ErrorMessage(
                LocalDateTime.now(),
                ex.getMessage());
    }

    @ExceptionHandler(GenericException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessage genericException(GenericException ex, WebRequest request) {

        return new ErrorMessage(
                LocalDateTime.now(),
                ex.getMessage());
    }
}

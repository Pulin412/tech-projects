package com.ns.iris.jokeapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(JokesNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> jokesNotFound(Exception ex) {
        Errors error = new Errors(HttpStatus.NOT_FOUND, LocalDateTime.now(), ex.getMessage());
        return  new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoDecentJokesFoundException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<?> noDecentJokesFound(Exception ex) {
        Errors error = new Errors(HttpStatus.NO_CONTENT, LocalDateTime.now(), ex.getMessage());
        return  new ResponseEntity<>(error,HttpStatus.NO_CONTENT);
    }
}

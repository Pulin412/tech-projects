package com.ns.iris.jokeapi.exception;

public class JokesNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public JokesNotFoundException(String message) {
        super(message);
    }
}

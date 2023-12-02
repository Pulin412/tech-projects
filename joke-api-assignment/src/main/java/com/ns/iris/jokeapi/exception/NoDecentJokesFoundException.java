package com.ns.iris.jokeapi.exception;

public class NoDecentJokesFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public NoDecentJokesFoundException(String message) {
        super(message);
    }
}

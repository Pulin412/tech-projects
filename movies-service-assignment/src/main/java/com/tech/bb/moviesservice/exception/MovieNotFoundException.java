package com.tech.bb.moviesservice.exception;

import java.io.Serial;

public class MovieNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public MovieNotFoundException(String msg) {
        super(msg);
    }
}

package com.tech.bb.moviesservice.exception;

import java.io.Serial;

public class GenericException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;
    public GenericException(String msg) {
        super(msg);
    }
}

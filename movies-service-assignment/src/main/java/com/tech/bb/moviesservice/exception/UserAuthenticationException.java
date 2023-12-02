package com.tech.bb.moviesservice.exception;

import java.io.Serial;

public class UserAuthenticationException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;
    public UserAuthenticationException(String msg) {
        super(msg);
    }
}

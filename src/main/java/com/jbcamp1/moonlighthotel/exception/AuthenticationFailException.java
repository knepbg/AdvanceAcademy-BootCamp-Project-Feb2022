package com.jbcamp1.moonlighthotel.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationFailException extends RuntimeException {

    private String field;

    public AuthenticationFailException(String message, String field) {
        super(message);
        this.field = field;
    }
}

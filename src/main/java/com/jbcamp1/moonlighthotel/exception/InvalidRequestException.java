package com.jbcamp1.moonlighthotel.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidRequestException extends RuntimeException {

    private String requestField;

    public InvalidRequestException(String message, String requestField) {
        super(message);
        this.requestField = requestField;
    }
}

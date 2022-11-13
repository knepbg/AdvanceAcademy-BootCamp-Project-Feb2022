package com.jbcamp1.moonlighthotel.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DuplicateRecordException extends RuntimeException {

    private String duplicateField;

    public DuplicateRecordException(String message, String duplicateField) {
        super(message);
        this.duplicateField = duplicateField;
    }
}

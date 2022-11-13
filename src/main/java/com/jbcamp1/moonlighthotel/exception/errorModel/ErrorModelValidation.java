package com.jbcamp1.moonlighthotel.exception.errorModel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
public class ErrorModelValidation {


    private String message;
    private Map<String, Set<String>> errors;

    public ErrorModelValidation(String message, Map<String, Set<String>> errors) {
        this.message = message;
        this.errors = errors;
    }
}

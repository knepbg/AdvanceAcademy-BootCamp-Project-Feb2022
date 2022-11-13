package com.jbcamp1.moonlighthotel.exception.errorModel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
public class ErrorModelAuthentication {

    private String message;
    private Map<String, Set<String>> errors;

    public ErrorModelAuthentication(String message, Map<String, Set<String>> errors) {
        this.message = message;
        this.errors = errors;
    }
}
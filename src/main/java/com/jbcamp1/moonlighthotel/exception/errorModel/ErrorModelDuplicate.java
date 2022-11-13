package com.jbcamp1.moonlighthotel.exception.errorModel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ErrorModelDuplicate {

    private String message;

    public ErrorModelDuplicate(String message) {
        this.message = message;

    }
}

package com.jbcamp1.moonlighthotel.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
public class CarFilter {

    @Schema(description = "Accommodation date. ISO-8601 in UTC string")
    private final String date;

    @Schema(description = "Number of seats")
    private final Integer seats;

    public boolean haveParametersBeenRequested() {
        return date != null && seats != null;
    }

}

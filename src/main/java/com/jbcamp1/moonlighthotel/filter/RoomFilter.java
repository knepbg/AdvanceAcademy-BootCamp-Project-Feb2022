package com.jbcamp1.moonlighthotel.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoomFilter {

    @Schema(description = "Accommodation date. ISO-8601 in UTC string")
    private final String startDate;

    @Schema(description = "Leaving date. ISO-8601 in UTC string")
    private final String endDate;

    @Schema(description = "Number of adults")
    private final Integer adults;

    @Schema(description = "Number of kids")
    private final Integer kids;

    public boolean haveParametersBeenRequested() {
        return startDate != null || endDate != null || adults != null || kids != null;
    }
}

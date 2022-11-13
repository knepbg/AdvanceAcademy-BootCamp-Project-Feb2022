package com.jbcamp1.moonlighthotel.dto.roomreservation.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RoomReservationSave {

    @NotNull(message = "The user is required.")
    @Schema(description = "User of reservation")
    private Long user;

    @NotBlank(message = "Please, enter start date")
    @Schema(description = "Accommodation date. ISO-8601 in UTC string")
    private String startDate;

    @NotBlank(message = "Please, enter end date")
    @Schema(description = "Leaving date. ISO-8601 in UTC string")
    private String endDate;

    @NotNull(message = "Please, enter a number of adults.")
    @Min(value = 1)
    @Max(value = 4)
    @Schema(description = "Number of adults", defaultValue = "0")
    private Integer adults;

    @NotNull(message = "Please, enter a number of kids.")
    @Min(value = 0)
    @Max(value = 4)
    @Schema(description = "Number of kids", defaultValue = "0")
    private Integer kids;

    @NotNull(message = "Bed type choice is required")
    @Schema(description = "Type of bed")
    private String typeBed;

    @NotNull(message = "View type choice is required")
    @Schema(description = "View of room")
    private String typeView;
}

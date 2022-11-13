package com.jbcamp1.moonlighthotel.dto.roomreservation.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jbcamp1.moonlighthotel.enumeration.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RoomReservationUpdate {

    @NotEmpty(message = "The user is required.")
    @Schema(description = "User of reservation")
    private Long user;

    @NotBlank(message = "Please, enter start date")
    @Schema(description = "Accommodation date. ISO-8601 in UTC string")
    private String startDate;

    @NotBlank(message = "Please, enter end date")
    @Schema(description = "Leaving date. ISO-8601 in UTC string")
    private String endDate;

    @NotEmpty
    @Size(min = 1, max = 4)
    @Schema(description = "Number of adults", defaultValue = "0")
    private Integer adults;

    @NotEmpty
    @Size(min = 1, max = 4)
    @Schema(description = "Number of kids", defaultValue = "0")
    private Integer kids;

    @NotEmpty
    @Schema(description = "Type of bed")
    private String typeBed;

    @NotEmpty
    @Schema(description = "View of room")
    private String typeView;

    @NotEmpty
    @Schema(description = "Status of reservation")
    private StatusType status;
}

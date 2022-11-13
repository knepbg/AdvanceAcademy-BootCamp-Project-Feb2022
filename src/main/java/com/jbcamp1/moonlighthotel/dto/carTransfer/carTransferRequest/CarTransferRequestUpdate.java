package com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class CarTransferRequestUpdate {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @NotBlank(message = "Please, enter date")
    @Schema(description = "Transfer date. ISO-8601 in UTC string")
    private String date;

    @NotNull(message = "Please, enter a number of adults.")
    @Min(value = 2)
    @Max(value = 8)
    @Schema(description = "Number of seats", defaultValue = "2")
    private Integer seats;

    @NotNull(message = "The user is required.")
    @Schema(description = "User of transfer")
    private Long user;
}

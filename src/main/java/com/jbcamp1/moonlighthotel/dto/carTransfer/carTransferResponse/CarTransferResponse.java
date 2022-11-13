package com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferResponse;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CarTransferResponse {

    private Long id;

    @Schema(description = "Car category")
    private String category;

    @Schema(description = "Car brand")
    private String brand;

    @Schema(description = "Car model")
    private String model;

    @Schema(description = "Image of car")
    private String image;

    @Schema(description = "Price for the car per one day")
    private Double price;

    @Schema(description = "Transfer date. ISO-8601 in UTC string")
    private String date;

    @Schema(description = "User ID")
    private Long userId;

    @Schema(description = "User name and surname")
    private String user;

    @Schema(description = "Reservation Date. ISO-8601 in UTC string")
    private String created;

}

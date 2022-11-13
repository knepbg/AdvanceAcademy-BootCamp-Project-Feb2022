package com.jbcamp1.moonlighthotel.dto.car.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CarResponse {

    private Long id;

    @Schema(description = "Car category")
    private String category;

    @Schema(description = "Car brand")
    private String brand;

    @Schema(description = "Car model")
    private String model;

    @Schema(description = "Image of car")
    private String image;

    @Schema(description = "Car images")
    private Set<String> images;

    @Schema(description = "Number of seats")
    private Integer seats;

    @Schema(description = "Price for the car per one day")
    private Double price;
}

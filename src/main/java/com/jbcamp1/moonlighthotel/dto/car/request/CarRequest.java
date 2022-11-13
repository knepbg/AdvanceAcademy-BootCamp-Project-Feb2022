package com.jbcamp1.moonlighthotel.dto.car.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CarRequest {

    @Min(value = 0, message = "Category should not be less than 0")
    @Schema(description = "Car category")
    private Long category;

    @Size(min = 2, max = 255)
    @NotBlank(message = "Please, enter a car brand.")
    @Schema(description = "Car brand")
    private String brand;

    @Size(min = 2, max = 255)
    @NotBlank(message = "Please, enter a car model.")
    @Schema(description = "Car model")
    private String model;

    @URL(message = "Please, enter a valid URL.")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)",
            message = "The image should be in one of the following formats: JPEG, JPG, PNG, GIF, BMP.")
    @NotBlank(message = "The image is required.")
    @Schema(description = "Image of car")
    private String image;

    @NotNull(message = "Images are required.")
    @Schema(description = "Images of car")
    private Set<@URL(message = "Please, enter a valid URL.")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)",
            message = "The image should be in one of the following formats: JPEG, JPG, PNG, GIF, BMP.")
            String> images;

    @Min(value = 0, message = "Year should not be less than 0")
    @NotNull(message = "Please, enter a correct year.")
    @Schema(description = "Year of manufacture of the car")
    private Integer year;
}

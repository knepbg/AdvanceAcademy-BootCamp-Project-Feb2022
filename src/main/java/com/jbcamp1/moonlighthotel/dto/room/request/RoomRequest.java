package com.jbcamp1.moonlighthotel.dto.room.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RoomRequest {

    @Length(min = 2, max = 255)
    @NotBlank(message = "The title is required.")
    @Schema(description = "Title of room")
    private String title;

    @URL(message = "Please, enter a valid URL.")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)",
            message = "The image should be in one of the following formats: JPEG, JPG, PNG, GIF, BMP.")
    @NotBlank(message = "The image is required.")
    @Schema(description = "Image of room")
    private String image;

    @NotNull(message = "Images are required.")
    @Schema(description = "Images of room")
    private Set<@URL(message = "Please, enter a valid URL.")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)",
            message = "The image should be in one of the following formats: JPEG, JPG, PNG, GIF, BMP.")
            String> images;

    @Length(min = 2, max = 255)
    @NotBlank(message = "Please, enter a description of the room.")
    @Schema(description = "Description of room")
    private String description;

    @Length(min = 2, max = 255)
    @NotBlank(message = "Please, enter in-room facilities.")
    @Schema(description = "Facilities of room")
    private String facilities;

    @Positive(message = "The value must be greater than 0.")
    @NotNull(message = "Please, enter the area of the room.")
    @Schema(description = "Room size")
    private Double area;

    @Positive(message = "The value must be greater than 0.")
    @NotNull(message = "Please, enter the maximum number of guests.")
    @Schema(description = "Number of people for the room")
    private Integer people;

    @Positive(message = "The value must be greater than 0.")
    @NotNull(message = "Please, enter a price per night.")
    @Schema(description = "Price for the room per one day")
    private Double price;

    @Positive(message = "The value must be greater than 0.")
    @NotNull(message = "Please, enter the total number of rooms of that type.")
    @Schema(description = "Available count of room")
    private Integer count;
}

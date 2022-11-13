package com.jbcamp1.moonlighthotel.dto.room.response;

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
public class RoomResponse {

    private Long id;

    @Schema(description = "Title (Category) of room")
    private String title;

    @Schema(description = "Image of room category")
    private String image;

    @Schema(description = "Images of room category")
    private Set<String> images;

    @Schema(description = "Description of room category")
    private String description;

    @Schema(description = "Facilities of room category")
    private String facilities;

    @Schema(description = "Room size")
    private Double area;

    @Schema(description = "Number of people for the room")
    private Integer people;

    @Schema(description = "Price for the room per one day")
    private Double price;
}

package com.jbcamp1.moonlighthotel.dto.roomreservation.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonPropertyOrder({"id", "room", "startDate", "endDate", "days", "adults", "kids", "price"})
public class RoomReservationSaveResponse {

    private Long id;

    private String room;

    private String startDate;

    private String endDate;

    private Integer days;

    private Integer adults;

    private Integer kids;

    private Double price;
}
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
@JsonPropertyOrder({"id", "room", "userId", "user", "startDate", "endDate", "days", "adults", "kids", "typeBed", "view", "price", "created", "status"})
public class RoomReservationResponse {

    private Long id;

    private String room;

    private Long userId;

    private String user;

    private String startDate;

    private String endDate;

    private Integer days;

    private Integer adults;

    private Integer kids;

    private String typeBed;

    private String typeView;

    private Double price;

    private String created;

    private String status;
}

package com.jbcamp1.moonlighthotel.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserRoomReservationResponse {

    private Long id;
    private String user;
    private String room;
    private Integer adults;
    private Integer kids;
    private String startDate;
    private String endDate;
    private Integer days;
    private String typeBed;
    private String typeView;
    private Double price;
    private String date;
    private String status;
}

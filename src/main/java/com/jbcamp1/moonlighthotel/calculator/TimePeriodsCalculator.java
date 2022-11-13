package com.jbcamp1.moonlighthotel.calculator;

import com.jbcamp1.moonlighthotel.model.RoomReservation;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class TimePeriodsCalculator {

    public Integer calculateRoomReservationDays(RoomReservation roomReservation) {
        Instant startDate = roomReservation.getStartDate();
        Instant endDate = roomReservation.getEndDate();
        Integer days = (int) Duration.between(startDate, endDate).toDays();
        return days;
    }
}

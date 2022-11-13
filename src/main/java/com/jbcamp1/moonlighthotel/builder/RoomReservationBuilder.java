package com.jbcamp1.moonlighthotel.builder;

import com.jbcamp1.moonlighthotel.calculator.PriceCalculator;
import com.jbcamp1.moonlighthotel.calculator.TimePeriodsCalculator;
import com.jbcamp1.moonlighthotel.enumeration.StatusType;
import com.jbcamp1.moonlighthotel.model.RoomReservation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class RoomReservationBuilder {

    @Autowired
    private final PriceCalculator priceCalculator;

    @Autowired
    private final TimePeriodsCalculator timePeriodsCalculator;

    public RoomReservation build(RoomReservation roomReservation) {
        Integer days = timePeriodsCalculator.calculateRoomReservationDays(roomReservation);
        Double price = priceCalculator.calculateRoomReservationPrice(roomReservation);
        return RoomReservation.builder()
                .id(roomReservation.getId())
                .room(roomReservation.getRoom())
                .user(roomReservation.getUser())
                .startDate(roomReservation.getStartDate())
                .endDate(roomReservation.getEndDate())
                .days(days)
                .adults(roomReservation.getAdults())
                .kids(roomReservation.getKids())
                .typeBed(roomReservation.getTypeBed())
                .typeView(roomReservation.getTypeView())
                .price(price)
                .created(Instant.now())
                .status(StatusType.NEW)
                .build();
    }

    public RoomReservation buildSummarize(RoomReservation roomReservation, Long rid) {
        Integer days = timePeriodsCalculator.calculateRoomReservationDays(roomReservation);
        Double price = priceCalculator.calculateRoomReservationPrice(roomReservation);
        RoomReservation summarizeResponse = RoomReservation.builder()
                .id(roomReservation.getUser().getId())
                .room(roomReservation.getRoom())
                .startDate(roomReservation.getStartDate())
                .endDate(roomReservation.getEndDate())
                .days(days)
                .adults(roomReservation.getAdults())
                .kids(roomReservation.getKids())
                .price(price)
                .build();
        return summarizeResponse;
    }

    public RoomReservation buildRoomReservationUpdate(RoomReservation reservationForUpdate, RoomReservation foundReservations) {
        Integer days = timePeriodsCalculator.calculateRoomReservationDays(reservationForUpdate);
        Double price = priceCalculator.calculateRoomReservationPrice(reservationForUpdate);
        RoomReservation updatedRoomReservation = RoomReservation.builder()
                .id(foundReservations.getId())
                .user(reservationForUpdate.getUser())
                .room(reservationForUpdate.getRoom())
                .startDate(reservationForUpdate.getStartDate())
                .endDate(reservationForUpdate.getEndDate())
                .days(days)
                .price(price)
                .adults(reservationForUpdate.getAdults())
                .kids(reservationForUpdate.getKids())
                .typeBed(reservationForUpdate.getTypeBed())
                .typeView(reservationForUpdate.getTypeView())
                .created(Instant.now())
                .status(reservationForUpdate.getStatus())
                .build();
        return updatedRoomReservation;
    }
}

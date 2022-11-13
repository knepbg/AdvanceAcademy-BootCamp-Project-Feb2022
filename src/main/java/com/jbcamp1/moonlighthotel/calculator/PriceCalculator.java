package com.jbcamp1.moonlighthotel.calculator;

import com.jbcamp1.moonlighthotel.model.RoomReservation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PriceCalculator {

    @Autowired
    private final TimePeriodsCalculator timePeriodsCalculator;

    public Double calculateRoomReservationPrice(RoomReservation roomReservation) {
        Double pricePurDay = roomReservation.getRoom().getPrice();
        Integer reservationDays = timePeriodsCalculator.calculateRoomReservationDays(roomReservation);
        Double reservationPrice = reservationDays * pricePurDay;
        return reservationPrice;
    }
}

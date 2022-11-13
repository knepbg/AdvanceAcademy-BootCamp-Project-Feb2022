package com.jbcamp1.moonlighthotel.validator;

import com.jbcamp1.moonlighthotel.exception.InvalidRequestException;
import com.jbcamp1.moonlighthotel.model.Room;
import com.jbcamp1.moonlighthotel.model.RoomReservation;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;

@Component
public class ParametersValidator {

    private static final String END_DATE = "end_date";
    private static final String START_DATE = "start_date";

    public void validateDates(Instant startDate, Instant endDate) {
        if (endDate.isBefore(startDate)) {
            throw new InvalidRequestException("End date cannot be date before start date.", END_DATE);
        } else if (startDate.isBefore(Instant.now()) ||
                (endDate.isBefore(Instant.now()))) {
            throw new InvalidRequestException(
                    String.format("Start date and end date cannot be before '%s'.", LocalDate.now()), START_DATE + " " + END_DATE);
        }
        if (startDate.equals(endDate)) {
            throw new InvalidRequestException("End date cannot be date as start date.", END_DATE);
        }
    }

    public void validateNumberOfGuests(RoomReservation roomReservation, Room foundRoom) {
        if (roomReservation.getAdults() > foundRoom.getPeople()) {
            throw new InvalidRequestException(
                    String.format("The number of guests should be equal or less than %s.", foundRoom.getPeople()), "adults");
        }
    }

    public void validateDate(Instant date) {
        if (date.isBefore(Instant.now())) {
            throw new InvalidRequestException(
                    String.format("Date cannot be before '%s'.", LocalDate.now()), date.toString());
        }
    }

}

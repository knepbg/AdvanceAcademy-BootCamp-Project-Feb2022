package com.jbcamp1.moonlighthotel.formatter;

import com.jbcamp1.moonlighthotel.model.RoomReservation;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class DateFormatter {

    public Instant stringToInstant(String date) {
        Instant startDate = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(date));
        return startDate;
    }

    public String instantToString(Instant date) {
        String instantStartDate = DateTimeFormatter.ISO_INSTANT.format(date);
        return instantStartDate;
    }

    public DateTimeFormatter formatterFromInstantForMessages() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC"));
        return formatter;
    }

    public String datePeriodAsString(RoomReservation roomReservation) {
        String start = formatterFromInstantForMessages().format(roomReservation.getStartDate());
        String end = formatterFromInstantForMessages().format(roomReservation.getEndDate());
        String period = String.format("%s - %s", start, end);
        return period;
    }
}

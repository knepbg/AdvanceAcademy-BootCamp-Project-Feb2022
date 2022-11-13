package com.jbcamp1.moonlighthotel.validator;

import com.jbcamp1.moonlighthotel.exception.InvalidRequestException;
import com.jbcamp1.moonlighthotel.model.Room;
import com.jbcamp1.moonlighthotel.model.RoomReservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@RunWith(MockitoJUnitRunner.class)
public class ParametersValidatorUnitTest {

    @InjectMocks
    private ParametersValidator parametersValidator;

    private Throwable thrown;

    private Instant endDate;

    private Instant startDate;

    @Test
    public void validateEndDateIsBeforeStartDateShouldTrowException() {
        endDate = Instant.now();
        startDate = endDate.plus(1, ChronoUnit.HOURS);
        thrown = catchThrowable(() -> parametersValidator.validateDates(startDate, endDate));
        assertThat(thrown).isInstanceOf(InvalidRequestException.class)
                .hasMessage("End date cannot be date before start date.");
    }

    @Test
    public void validateStartDateIsBeforeNowShouldTrowException() {
        startDate = Instant.now().minus(1, ChronoUnit.HOURS);
        endDate = startDate.plus(1, ChronoUnit.DAYS);
        thrown = catchThrowable(() -> parametersValidator.validateDates(startDate, endDate));
        assertThat(thrown).isInstanceOf(InvalidRequestException.class)
                .hasMessage("Start date and end date cannot be before '%s'.", LocalDate.now());
    }

    @Test
    public void validateStartDateIsEqualEndDateShouldTrowException() {
        startDate = Instant.now().plus(1, ChronoUnit.DAYS);
        endDate = startDate;
        thrown = catchThrowable(() -> parametersValidator.validateDates(startDate, endDate));
        assertThat(thrown).isInstanceOf(InvalidRequestException.class)
                .hasMessage("End date cannot be date as start date.");
    }

    @Test
    public void validateNumberOfGuests() {
        RoomReservation roomReservation = RoomReservation.builder()
                .adults(5)
                .build();
        Room foundRoom = Room.builder()
                .people(3)
                .build();
        thrown = catchThrowable(() -> parametersValidator.validateNumberOfGuests(roomReservation, foundRoom));
        assertThat(thrown).isInstanceOf(InvalidRequestException.class)
                .hasMessage("The number of guests should be equal or less than %s.", foundRoom.getPeople());
    }
}

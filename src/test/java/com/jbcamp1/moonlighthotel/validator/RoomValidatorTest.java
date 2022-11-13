package com.jbcamp1.moonlighthotel.validator;

import com.jbcamp1.moonlighthotel.enumeration.BedType;
import com.jbcamp1.moonlighthotel.enumeration.ViewType;
import com.jbcamp1.moonlighthotel.exception.DuplicateRecordException;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.formatter.DateFormatter;
import com.jbcamp1.moonlighthotel.model.Room;
import com.jbcamp1.moonlighthotel.model.RoomReservation;
import com.jbcamp1.moonlighthotel.repository.RoomRepository;
import com.jbcamp1.moonlighthotel.repository.RoomReservationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomValidatorTest {

    private Throwable thrown;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomReservationRepository roomReservationRepository;

    @Mock
    private ParametersValidator parametersValidator;

    @Mock
    private DateFormatter dateFormatter;

    @InjectMocks
    private RoomValidator roomValidator;

    @Test
    public void validateThatDuplicateTitleShouldThrowDuplicateRecordException() {
        Long id = 1L;
        String title = "Standard Double Room";

        Room room = Room.builder()
                .id(id)
                .title(title)
                .build();

        when(roomRepository.findByTitle(title))
                .thenReturn(Room.builder().id(2L).build());

        thrown = catchThrowable(() -> roomValidator.validateTitle(room, id));

        assertThat(thrown)
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage("Room with title '%s' already exists.", title);
    }

    @Test
    public void validateThatIdOfNonExistentRoomShouldThrowRecordNotFoundException() {
        Long id = 10L;

        when(roomRepository.findById(id))
                .thenReturn(Optional.empty());

        thrown = catchThrowable(() -> roomValidator.verifyRoomExists(id));

        assertThat(thrown)
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage("Room with id '%s' does not exist.", id);
    }

    @Test
    public void validateThatWhenARoomIsNotAvailableForTheSelectedPeriodShouldThrowException() {
        Long id = 1L;
        String title = "Standard Double Room";

        Instant startDate = Instant.now().plus(1, ChronoUnit.DAYS);
        Instant endDate = startDate.plus(5, ChronoUnit.DAYS);

        Room room = Room.builder()
                .id(id)
                .title(title)
                .count(2)
                .build();

        RoomReservation roomReservation = RoomReservation.builder()
                .id(id)
                .room(room)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        Integer roomReservationsCount = 5;
        String period = startDate.toString() + " - " + endDate.toString();

        when(roomReservationRepository.countOfRoomReservationsByPeriod(startDate, endDate, id))
                .thenReturn(roomReservationsCount);

        when(dateFormatter.datePeriodAsString(roomReservation)).thenReturn(period);

        thrown = catchThrowable(() -> roomValidator.validateForAvailableRooms(room, roomReservation));

        assertThat(thrown)
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage("There is no available '%s' for the selected period: %s. " +
                        "Please, try another period or check for another room.", room.getTitle(), period);
    }

    @Test
    public void testValidateRoom() {
        Long id = 1L;
        String title = "Standard Double Room";

        Room room = Room.builder()
                .id(id)
                .title(title)
                .build();

        roomValidator.validateRoom(room);

        verify(roomRepository, times(1)).findByTitle(title);
    }

    @Test
    public void testValidateRoomForUpdate() {
        Long id = 1L;
        String title = "Standard Double Room";

        Room room = Room.builder()
                .id(id)
                .title(title)
                .build();

        when(roomRepository.findById(id))
                .thenReturn(Optional.of(Room.builder().build()));

        when(roomRepository.findByTitle(title))
                .thenReturn(null);

        roomValidator.validateRoomForUpdate(room, id);

        verify(roomRepository, times(1)).findById(id);
        verify(roomRepository, times(1)).findByTitle(title);
    }

    @Test
    public void testValidateTitle() {
        Long id = 1L;
        String title = "Standard Double Room";

        Room room = Room.builder()
                .id(id)
                .title(title)
                .build();

        roomValidator.validateTitle(room, id);

        verify(roomRepository, times(1)).findByTitle(title);
    }

    @Test
    public void testVerifyRoomExists() {
        Long id = 1L;

        when(roomRepository.findById(id))
                .thenReturn(Optional.of(Room.builder().build()));

        roomValidator.verifyRoomExists(id);

        verify(roomRepository, times(1)).findById(id);
    }

    @Test
    public void testValidateRequestQueryParam() {
        Instant startDate = Instant.now().plus(1, ChronoUnit.DAYS);
        Instant endDate = startDate.plus(5, ChronoUnit.DAYS);

        roomValidator.validateRequestQueryParam(startDate, endDate);

        verify(parametersValidator, times(1)).validateDates(startDate, endDate);
    }

    @Test
    public void testValidateForAvailableRooms() {
        Long id = 1L;
        String title = "Standard Double Room";

        Instant startDate = Instant.now().plus(1, ChronoUnit.DAYS);
        Instant endDate = startDate.plus(5, ChronoUnit.DAYS);

        Room room = Room.builder()
                .id(id)
                .title(title)
                .count(7)
                .build();

        RoomReservation roomReservation = RoomReservation.builder()
                .id(id)
                .startDate(startDate)
                .endDate(endDate)
                .typeBed(BedType.ONE)
                .typeView(ViewType.GARDEN)
                .build();

        roomValidator.validateForAvailableRooms(room, roomReservation);

        verify(roomReservationRepository, times(1))
                .countOfRoomReservationsByPeriod(startDate, endDate, id);

        verify(dateFormatter, times(1)).datePeriodAsString(roomReservation);
    }
}

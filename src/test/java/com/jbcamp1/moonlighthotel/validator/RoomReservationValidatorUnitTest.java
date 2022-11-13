package com.jbcamp1.moonlighthotel.validator;

import com.jbcamp1.moonlighthotel.enumeration.BedType;
import com.jbcamp1.moonlighthotel.enumeration.ViewType;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.model.Room;
import com.jbcamp1.moonlighthotel.model.RoomReservation;
import com.jbcamp1.moonlighthotel.repository.RoomReservationRepository;
import com.jbcamp1.moonlighthotel.service.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomReservationValidatorUnitTest {

    @Mock
    private RoomService roomService;

    @Mock
    private UserValidator userValidation;

    @Mock
    private RoomReservationRepository roomReservationRepository;

    @Mock
    private ParametersValidator parametersValidator;

    @Mock
    private RoomValidator roomValidator;

    @InjectMocks
    private RoomReservationValidator roomReservationValidator;

    private final Long rid = 1L;

    private final Long id = 1L;

    private Room room;

    private RoomReservation roomReservation;

    @Before
    public void roomReservation() {
        this.roomReservation = RoomReservation.builder()
                .startDate(Instant.now())
                .endDate(Instant.now())
                .typeBed(BedType.ONE)
                .typeView(ViewType.GARDEN)
                .build();
    }

    @Test
    public void validateRoomReservation() {

        roomReservationValidator.validateRoomReservation(roomReservation, rid);

        verify(roomService, times(1)).findById(id);
        verify(parametersValidator, times(1)).validateDates(roomReservation.getStartDate(), roomReservation.getEndDate());
        verify(parametersValidator, times(1)).validateNumberOfGuests(roomReservation, room);
        verify(roomValidator, times(1)).validateForAvailableRooms(room, roomReservation);
    }

    @Test
    public void validateRoomSummarize() {

        roomReservationValidator.validateRoomSummarize(roomReservation, rid);

        verify(roomService, times(1)).findById(anyLong());
        verify(parametersValidator, times(1)).validateDates(roomReservation.getStartDate(), roomReservation.getEndDate());
        verify(parametersValidator, times(1)).validateNumberOfGuests(roomReservation, room);
        verify(roomValidator, times(1)).validateForAvailableRooms(room, roomReservation);
    }

    @Test
    public void validateWhenNoRoomReservationFoundTrowCorrectException() {

        when(roomReservationRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> roomReservationValidator.validateRoomReservationExisting(rid));

        assertThat(thrown).isInstanceOf(RecordNotFoundException.class)
                .hasMessage("Reservation with id %d is not found", rid);
    }

    @Test
    public void validateFindRoomReservationByIdAndUserId() {

        when(roomReservationRepository.findById(rid)).thenReturn(Optional.of(roomReservation));

        roomReservationValidator.validateFindRoomReservationByIdAndUserId(id, rid);

        verify(userValidation, times(1)).validateUserExisting(id);
        verify(roomReservationRepository, times(1)).findById(rid);
    }

    @Test
    public void validateFindRoomReservationByIdAndRoomId() {

        when(roomService.findById(id)).thenReturn(room);
        when(roomReservationRepository.findById(rid)).thenReturn(Optional.of(roomReservation));

        roomReservationValidator.validateFindRoomReservationByIdAndRoomId(id, rid);

        verify(roomService, times(1)).findById(id);
        verify(roomReservationRepository, times(1)).findById(rid);
    }
}

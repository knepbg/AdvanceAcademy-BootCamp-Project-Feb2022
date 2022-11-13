package com.jbcamp1.moonlighthotel.validator;

import com.jbcamp1.moonlighthotel.exception.DuplicateRecordException;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.formatter.DateFormatter;
import com.jbcamp1.moonlighthotel.model.Room;
import com.jbcamp1.moonlighthotel.model.RoomReservation;
import com.jbcamp1.moonlighthotel.repository.RoomRepository;
import com.jbcamp1.moonlighthotel.repository.RoomReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@AllArgsConstructor
@Component
public class RoomValidator {

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final RoomReservationRepository roomReservationRepository;

    @Autowired
    private final ParametersValidator parametersValidator;

    @Autowired
    private final DateFormatter dateFormatter;

    public void validateRoom(Room room) {
        validateTitle(room, room.getId());
    }

    public void validateRoomForUpdate(Room room, Long id) {
        verifyRoomExists(id);
        validateTitle(room, id);
    }

    public void validateTitle(Room room, Long id) {
        Room foundRoom = roomRepository.findByTitle(room.getTitle());

        if (foundRoom != null && !foundRoom.getId().equals(id)) {
            throw new DuplicateRecordException(
                    String.format("Room with title '%s' already exists.", room.getTitle()), "title");
        }
    }

    public void verifyRoomExists(Long id) {
        roomRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("Room with id '%s' does not exist.", id)));
    }

    public void validateRequestQueryParam(Instant startDate, Instant endDate) {
        parametersValidator.validateDates(startDate, endDate);
    }

    public void validateForAvailableRooms(Room room, RoomReservation roomReservation) {
        Integer reservationsCount = roomReservationRepository
                .countOfRoomReservationsByPeriod(roomReservation.getStartDate(), roomReservation.getEndDate(), room.getId());
        String period = dateFormatter.datePeriodAsString(roomReservation);
        if (reservationsCount != null && reservationsCount >= room.getCount()) {
            throw new DuplicateRecordException(
                    String.format("There is no available '%s' for the selected period: %s. " +
                            "Please, try another period or check for another room.", room.getTitle(), period), "room");
        }
    }
}

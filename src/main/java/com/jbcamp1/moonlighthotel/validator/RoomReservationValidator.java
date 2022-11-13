package com.jbcamp1.moonlighthotel.validator;

import com.jbcamp1.moonlighthotel.enumeration.BedType;
import com.jbcamp1.moonlighthotel.enumeration.ViewType;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.model.Room;
import com.jbcamp1.moonlighthotel.model.RoomReservation;
import com.jbcamp1.moonlighthotel.repository.RoomReservationRepository;
import com.jbcamp1.moonlighthotel.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RoomReservationValidator {

    @Autowired
    private final RoomService roomService;

    @Autowired
    private final UserValidator userValidation;

    @Autowired
    private final RoomReservationRepository roomReservationRepository;

    @Autowired
    private final ParametersValidator parametersValidator;

    @Autowired
    private final RoomValidator roomValidator;


    public void validateRoomReservation(RoomReservation roomReservation, Long rid) {
        Room foundRoom = roomService.findById(rid);
        parametersValidator.validateDates(roomReservation.getStartDate(), roomReservation.getEndDate());
        parametersValidator.validateNumberOfGuests(roomReservation, foundRoom);
        roomValidator.validateForAvailableRooms(foundRoom, roomReservation);
        BedType.findByName(roomReservation.getTypeBed().name());
        ViewType.findByName(roomReservation.getTypeView().name());
    }

    public void validateRoomSummarize(RoomReservation roomReservation, Long rid) {
        Room foundRoom = roomService.findById(rid);
        parametersValidator.validateDates(roomReservation.getStartDate(), roomReservation.getEndDate());
        parametersValidator.validateNumberOfGuests(roomReservation, foundRoom);
        roomValidator.validateForAvailableRooms(foundRoom, roomReservation);
        BedType.findByName(roomReservation.getTypeBed().name());
        ViewType.findByName(roomReservation.getTypeView().name());
    }

    public void validateFindRoomReservationByIdAndUserId(Long uid, Long rid) {
        userValidation.validateUserExisting(uid);
        validateRoomReservationExisting(rid);
    }

    public void validateFindRoomReservationByIdAndRoomId(Long id, Long rid) {
        roomService.findById(id);
        validateRoomReservationExisting(rid);
    }

    public void validateRoomReservationExisting(Long rid) {
        roomReservationRepository.findById(rid).orElseThrow(() -> new RecordNotFoundException(String
                .format("Reservation with id %d is not found", rid)));
    }
}
package com.jbcamp1.moonlighthotel.service.impl;

import com.jbcamp1.moonlighthotel.builder.RoomReservationBuilder;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.model.RoomReservation;
import com.jbcamp1.moonlighthotel.model.User;
import com.jbcamp1.moonlighthotel.repository.RoomReservationRepository;
import com.jbcamp1.moonlighthotel.service.RoomReservationService;
import com.jbcamp1.moonlighthotel.service.UserService;
import com.jbcamp1.moonlighthotel.validator.RoomReservationValidator;
import com.jbcamp1.moonlighthotel.validator.RoomValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoomReservationServiceImpl implements RoomReservationService {

    @Autowired
    private final RoomReservationBuilder roomReservationBuilder;

    @Autowired
    private final RoomReservationRepository roomReservationRepository;

    @Autowired
    private final RoomReservationValidator roomReservationValidator;

    @Autowired
    private final RoomValidator roomValidator;

    @Autowired
    private final UserService userService;


    @Override
    public List<RoomReservation> findAllRoomReservationById(Long id) {
        roomValidator.verifyRoomExists(id);
        return roomReservationRepository.findAllRoomReservationByRoomId(id);
    }

    @Override
    public RoomReservation save(RoomReservation roomReservation, Long rid) {
        roomReservationValidator.validateRoomReservation(roomReservation, rid);
        RoomReservation builtRoomReservation = roomReservationBuilder.build(roomReservation);
        return roomReservationRepository.save(builtRoomReservation);
    }

    @Override
    public RoomReservation update(RoomReservation reservationForUpdate, Long id, Long rid) {
        RoomReservation foundRoomReservation = findRoomReservationForRoom(id, rid);
        roomReservationValidator.validateRoomReservation(reservationForUpdate, id);
        RoomReservation updatedRoomReservation = roomReservationBuilder
                .buildRoomReservationUpdate(reservationForUpdate, foundRoomReservation);
        return roomReservationRepository.save(updatedRoomReservation);
    }

    @Override
    public RoomReservation findRoomReservationForRoom(Long id, Long rid) {
        roomReservationValidator.validateFindRoomReservationByIdAndRoomId(id, rid);
        RoomReservation foundReservation = findRoomReservationByIdAndRoomId(rid, id);
        return foundReservation;
    }

    @Override
    public RoomReservation summarize(RoomReservation reservationSummarize, Long rid) {
        roomReservationValidator.validateRoomSummarize(reservationSummarize, rid);
        RoomReservation summarizeReservation = roomReservationBuilder.buildSummarize(reservationSummarize, rid);
        return summarizeReservation;
    }

    @Override
    public Set<RoomReservation> findAllById(Long uid) {
        User user = userService.findById(uid);
        Set<RoomReservation> reservationsUser = user.getRoomReservations();
        return reservationsUser;
    }

    @Override
    public RoomReservation findRoomReservationForUser(Long uid, Long rid) {
        roomReservationValidator.validateFindRoomReservationByIdAndUserId(uid, rid);
        RoomReservation roomReservation = roomReservationRepository
                .findRoomReservationByIdAndUserId(rid, uid)
                .orElseThrow(() ->
                        new RecordNotFoundException(String
                                .format("Selected user doesn't have room reservation with id %d", rid)));
        return roomReservation;
    }

    @Override
    public void delete(Long id, Long rid) {
        RoomReservation foundReservation = findRoomReservationByIdAndRoomId(rid, id);
        roomReservationRepository.deleteById(foundReservation.getId());
    }

    private RoomReservation findRoomReservationByIdAndRoomId(Long rid, Long id) {
        RoomReservation foundReservation = roomReservationRepository.findRoomReservationByIdAndRoomId(rid, id)
                .orElseThrow(() -> new RecordNotFoundException(String
                        .format("Selected room doesn't have reservation with id %d", rid)));
        return foundReservation;
    }
    @Override
    public List<RoomReservation> findAllRoomReservation() {
        return roomReservationRepository.findAll();
    }

}


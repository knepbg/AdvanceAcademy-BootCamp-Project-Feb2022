package com.jbcamp1.moonlighthotel.service;

import com.jbcamp1.moonlighthotel.model.RoomReservation;

import java.util.List;
import java.util.Set;

public interface RoomReservationService {

    RoomReservation update(RoomReservation roomReservation, Long id, Long rid);

    List<RoomReservation> findAllRoomReservationById(Long id);

    List<RoomReservation> findAllRoomReservation();

    RoomReservation save(RoomReservation roomReservation, Long id);

    RoomReservation summarize(RoomReservation reservation, Long rid);

    Set<RoomReservation> findAllById(Long uid);

    RoomReservation findRoomReservationForUser(Long uid, Long rid);

    void delete(Long id, Long rid);

    RoomReservation findRoomReservationForRoom(Long id, Long rid);

}

package com.jbcamp1.moonlighthotel.service;

import com.jbcamp1.moonlighthotel.filter.RoomFilter;
import com.jbcamp1.moonlighthotel.model.Room;

import java.time.Instant;
import java.util.List;

public interface RoomService {

    Room findById(Long id);

    Room update(Room room, Long id);

    Room save(Room room);

    List<Room> findAll();

    List<Room> findAllAvailableRooms(RoomFilter roomFilter);

    void deleteById(Long id);

    List<Room> checkForQueryParameters(RoomFilter roomFilter);
}

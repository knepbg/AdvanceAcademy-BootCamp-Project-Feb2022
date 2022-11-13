package com.jbcamp1.moonlighthotel.service.impl;

import com.jbcamp1.moonlighthotel.builder.RoomBuilder;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.filter.RoomFilter;
import com.jbcamp1.moonlighthotel.formatter.DateFormatter;
import com.jbcamp1.moonlighthotel.model.Room;
import com.jbcamp1.moonlighthotel.repository.RoomRepository;
import com.jbcamp1.moonlighthotel.service.RoomService;
import com.jbcamp1.moonlighthotel.validator.RoomValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final RoomBuilder roomBuilder;

    @Autowired
    private final RoomValidator roomValidator;

    @Autowired
    private final DateFormatter dateFormatter;

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String
                        .format("Room with id %d does not exist", id)));
    }

    @Override
    public Room update(Room room, Long id) {
        roomValidator.validateRoomForUpdate(room, id);
        Room builtRoom = roomBuilder.build(room, id);

        return roomRepository.save(builtRoom);
    }

    @Override
    public Room save(Room room) {
        roomValidator.validateRoom(room);
        Room builtRoom = roomBuilder.build(room);

        return roomRepository.save(builtRoom);
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> findAllAvailableRooms(RoomFilter roomFilter) {
        Instant startDate = dateFormatter.stringToInstant(roomFilter.getStartDate());
        Instant endDate = dateFormatter.stringToInstant(roomFilter.getEndDate());

        roomValidator.validateRequestQueryParam(startDate, endDate);
        return roomRepository.findAllAvailableRooms(startDate, endDate, roomFilter.getAdults(), roomFilter.getKids());
    }

    @Override
    public void deleteById(Long id) {
        Room foundRoom = findById(id);

        roomRepository.deleteById(foundRoom.getId());
    }

    @Override
    public List<Room> checkForQueryParameters(RoomFilter roomFilter) {
        boolean hasParameters = roomFilter.haveParametersBeenRequested();

        if (!hasParameters) {
            return findAll();
        } else {
            return findAllAvailableRooms(roomFilter);
        }
    }
}

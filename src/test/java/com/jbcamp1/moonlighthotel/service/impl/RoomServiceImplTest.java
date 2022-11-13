package com.jbcamp1.moonlighthotel.service.impl;

import com.jbcamp1.moonlighthotel.builder.RoomBuilder;
import com.jbcamp1.moonlighthotel.filter.RoomFilter;
import com.jbcamp1.moonlighthotel.formatter.DateFormatter;
import com.jbcamp1.moonlighthotel.model.Room;
import com.jbcamp1.moonlighthotel.repository.RoomRepository;
import com.jbcamp1.moonlighthotel.validator.RoomValidator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomBuilder roomBuilder;

    @Mock
    private RoomValidator roomValidator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private DateFormatter dateFormatter;

    @Mock
    private RoomFilter roomFilter;

    @InjectMocks
    private RoomServiceImpl roomServiceImpl;
    private RoomBuilder builtRoom;
    private Instant startDate;
    private Instant endDate;
    private Integer adults;
    private Integer kids;
    private Room room;


    @Test
    public void verifyFindById() {
        Long id = 5L;
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(Room.builder().build()));
        roomServiceImpl.findById(id);
        verify(roomRepository, times(1)).findById(id);
    }

    @Test
    public void verifyUpdate() {
        Long id = 11L;
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(Room.builder().build()));
        roomServiceImpl.findById(id);
        verify(roomRepository, times(1)).findById(id);

    }

    @Test
    public void verifySave() {
        Room roomServiceSaveRoom = new Room();
        roomServiceImpl.save(roomServiceSaveRoom);

        verify(roomValidator, times(1)).validateRoom(any());
        verify(roomBuilder, times(1)).build(any());
        verify(roomRepository, times(1)).save(any());


    }

    @Test
    public void verifyDeleteById() {
        Long id = 10L;
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(Room.builder().build()));
        roomServiceImpl.findById(id);
        verify(roomRepository, times(1)).findById(id);
    }

    @Test
    public void findAllAvailableRooms() {
        RoomFilter roomFilter = new RoomFilter("2023-04-10T11:11:23.497467Z", "2023-04-12T11:11:23.497467Z", adults, kids);
        when(roomRepository.findAllAvailableRooms(startDate, endDate, adults, kids)).thenReturn(Collections.emptyList());
        roomServiceImpl.findAllAvailableRooms(roomFilter);
        verify(roomRepository, times(1)).findAllAvailableRooms(startDate, endDate, adults, kids);
    }
}

package com.jbcamp1.moonlighthotel.converter;

import com.jbcamp1.moonlighthotel.dto.room.request.RoomRequest;
import com.jbcamp1.moonlighthotel.dto.room.response.RoomResponse;
import com.jbcamp1.moonlighthotel.model.Image;
import com.jbcamp1.moonlighthotel.model.Room;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoomConverter {

    public Room toRoom(RoomRequest roomRequest) {
        return Room.builder()
                .title(roomRequest.getTitle())
                .image(roomRequest.getImage())
                .images(roomRequest.getImages().stream()
                        .map(image -> Image.builder().imagePath(image).build())
                        .collect(Collectors.toSet()))
                .description(roomRequest.getDescription())
                .facilities(roomRequest.getFacilities())
                .area(roomRequest.getArea())
                .people(roomRequest.getPeople())
                .price(roomRequest.getPrice())
                .count(roomRequest.getCount())
                .build();
    }

    public RoomResponse toRoomResponse(Room room) {
        Set<String> images = room.getImages().stream()
                .map(Image::getImagePath)
                .collect(Collectors.toSet());

        return RoomResponse.builder()
                .id(room.getId())
                .title(room.getTitle())
                .image(room.getImage())
                .images(images)
                .description(room.getDescription())
                .facilities(room.getFacilities())
                .area(room.getArea())
                .people(room.getPeople())
                .price(room.getPrice())
                .build();
    }
}
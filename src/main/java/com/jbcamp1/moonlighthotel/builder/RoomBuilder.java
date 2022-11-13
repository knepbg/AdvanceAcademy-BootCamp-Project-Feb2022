package com.jbcamp1.moonlighthotel.builder;

import com.jbcamp1.moonlighthotel.model.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomBuilder {

    public Room build(Room room) {
        return Room.builder()
                .id(room.getId())
                .title(room.getTitle())
                .image(room.getImage())
                .images(room.getImages())
                .description(room.getDescription())
                .facilities(room.getFacilities())
                .area(room.getArea())
                .people(room.getPeople())
                .price(room.getPrice())
                .count(room.getCount())
                .build();
    }

    public Room build(Room room, Long id) {
        return Room.builder()
                .id(id)
                .title(room.getTitle())
                .image(room.getImage())
                .images(room.getImages())
                .description(room.getDescription())
                .facilities(room.getFacilities())
                .area(room.getArea())
                .people(room.getPeople())
                .price(room.getPrice())
                .count(room.getCount())
                .build();
    }
}

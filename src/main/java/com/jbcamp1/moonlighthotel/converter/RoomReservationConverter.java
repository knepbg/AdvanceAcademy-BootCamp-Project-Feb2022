package com.jbcamp1.moonlighthotel.converter;

import com.jbcamp1.moonlighthotel.dto.roomreservation.request.RoomReservationSave;
import com.jbcamp1.moonlighthotel.dto.roomreservation.request.RoomReservationUpdate;
import com.jbcamp1.moonlighthotel.dto.roomreservation.response.RoomReservationResponse;
import com.jbcamp1.moonlighthotel.dto.roomreservation.response.RoomReservationSaveResponse;
import com.jbcamp1.moonlighthotel.enumeration.BedType;
import com.jbcamp1.moonlighthotel.enumeration.ViewType;
import com.jbcamp1.moonlighthotel.formatter.DateFormatter;
import com.jbcamp1.moonlighthotel.model.RoomReservation;
import com.jbcamp1.moonlighthotel.model.User;
import com.jbcamp1.moonlighthotel.service.RoomService;
import com.jbcamp1.moonlighthotel.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class RoomReservationConverter {

    @Autowired
    private final UserService userService;

    @Autowired
    private final RoomService roomService;

    @Autowired
    private final DateFormatter dateFormatter;

    public RoomReservation toRoomReservation(RoomReservationSave roomReservationSave, Long rid) {
        User foundUser = userService.findById(roomReservationSave.getUser());
        return RoomReservation.builder()
                .user(foundUser)
                .room(roomService.findById(rid))
                .startDate(dateFormatter.stringToInstant(roomReservationSave.getStartDate()))
                .endDate(dateFormatter.stringToInstant(roomReservationSave.getEndDate()))
                .adults(roomReservationSave.getAdults())
                .kids(roomReservationSave.getKids())
                .typeBed(BedType.valueOf(roomReservationSave.getTypeBed()))
                .typeView(ViewType.valueOf(roomReservationSave.getTypeView()))
                .build();
    }

    public RoomReservation toRoomReservation(RoomReservationUpdate roomReservationUpdate, Long rid) {
        User foundUser = userService.findById(roomReservationUpdate.getUser());
        return RoomReservation.builder()
                .user(foundUser)
                .room(roomService.findById(rid))
                .startDate(dateFormatter.stringToInstant(roomReservationUpdate.getStartDate()))
                .endDate(dateFormatter.stringToInstant(roomReservationUpdate.getEndDate()))
                .adults(roomReservationUpdate.getAdults())
                .kids(roomReservationUpdate.getKids())
                .typeBed(BedType.valueOf(roomReservationUpdate.getTypeBed()))
                .typeView(ViewType.valueOf(roomReservationUpdate.getTypeView()))
                .status(roomReservationUpdate.getStatus())
                .build();
    }

    public RoomReservationSaveResponse toRoomReservationSaveResponse(RoomReservation roomReservation) {
        return RoomReservationSaveResponse.builder()
                .id(roomReservation.getId())
                .room(roomReservation.getRoom().getTitle())
                .startDate(dateFormatter.instantToString(roomReservation.getStartDate()))
                .endDate(dateFormatter.instantToString(roomReservation.getEndDate()))
                .days(roomReservation.getDays())
                .adults(roomReservation.getAdults())
                .kids(roomReservation.getKids())
                .price(roomReservation.getPrice())
                .build();
    }

    public RoomReservationResponse toRoomReservationResponse(RoomReservation roomReservation) {
        return RoomReservationResponse.builder()
                .id(roomReservation.getId())
                .room(roomReservation.getRoom().getTitle())
                .userId(roomReservation.getUser().getId())
                .user(roomReservation.getUser().getFullName())
                .startDate(dateFormatter.instantToString(roomReservation.getStartDate()))
                .endDate(dateFormatter.instantToString(roomReservation.getEndDate()))
                .days(roomReservation.getDays())
                .adults(roomReservation.getAdults())
                .kids(roomReservation.getKids())
                .typeBed(roomReservation.getTypeBed().name())
                .typeView(roomReservation.getTypeView().name())
                .price(roomReservation.getPrice())
                .created(dateFormatter.instantToString(roomReservation.getCreated()))
                .status(roomReservation.getStatus().name())
                .build();
    }
}

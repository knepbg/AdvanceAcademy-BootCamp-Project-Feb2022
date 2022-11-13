package api.roomreservation.creator;

import api.ObjectCreator;
import com.jbcamp1.moonlighthotel.dto.roomreservation.request.RoomReservationSave;

public class RoomReservationCreator {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public RoomReservationSave createRoomReservation() {
        String content = "classpath:roomReservationSave.json";
        return objectCreator.createObject(content, RoomReservationSave.class);
    }

    public RoomReservationSave createRoomReservationBadRequest() {
        String content = "classpath:roomReservationSaveBadRequest.json";
        return objectCreator.createObject(content, RoomReservationSave.class);
    }
}

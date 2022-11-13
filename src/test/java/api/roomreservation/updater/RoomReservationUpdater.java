package api.roomreservation.updater;

import api.ObjectCreator;
import com.jbcamp1.moonlighthotel.dto.roomreservation.request.RoomReservationUpdate;

public class RoomReservationUpdater {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public RoomReservationUpdate updateRoomReservation() {
        String content = "classpath:roomReservationUpdate.json";

        return objectCreator.createObject(content, RoomReservationUpdate.class);
    }

    public RoomReservationUpdate updateRoomReservationBadRequest() {
        String content = "classpath:roomReservationUpdateBadRequest.json";

        return objectCreator.createObject(content, RoomReservationUpdate.class);
    }
}
package api.roomreservation;

import api.BaseApiTest;
import api.roomreservation.creator.RoomReservationCreator;
import api.roomreservation.updater.RoomReservationUpdater;
import com.jbcamp1.moonlighthotel.dto.roomreservation.request.RoomReservationSave;
import com.jbcamp1.moonlighthotel.dto.roomreservation.request.RoomReservationUpdate;
import com.jbcamp1.moonlighthotel.dto.roomreservation.response.RoomReservationResponse;
import com.jbcamp1.moonlighthotel.dto.roomreservation.response.RoomReservationSaveResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class UpdateRoomReservationApiTest extends BaseApiTest {

    private static final String URI = "/rooms/{id}/reservations";
    private final RoomReservationCreator roomReservationCreator = new RoomReservationCreator();
    private final RoomReservationUpdater roomReservationUpdater = new RoomReservationUpdater();

    @Test
    public void updateRoomReservationWithAdminTokenShouldReturnOK() {
        Long rid = createAndSaveRoomReservationBeforeTest();

        RoomReservationUpdate updatedRoomReservation = roomReservationUpdater.updateRoomReservation();

        RoomReservationResponse roomReservationResponse =
                getClientWithAdminToken()
                        .pathParam("id", 1)
                        .pathParam("rid", rid)
                        .contentType(ContentType.JSON)
                        .body(updatedRoomReservation)
                        .when()
                        .put(URI + "/{rid}")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .contentType(ContentType.JSON)
                        .extract()
                        .as(RoomReservationResponse.class);

        Long updatedReservationId = roomReservationResponse.getId();
        deleteRoomReservationAfterTest(updatedReservationId);
    }

    @Test
    public void updateRoomReservationWithAdminTokenAndInvalidDataShouldReturnBadRequest() {
        Long rid = createAndSaveRoomReservationBeforeTest();

        RoomReservationUpdate updatedRoomReservation = roomReservationUpdater.updateRoomReservationBadRequest();

        getClientWithAdminToken()
                .pathParam("id", 1)
                .pathParam("rid", rid)
                .contentType(ContentType.JSON)
                .body(updatedRoomReservation)
                .when()
                .put(URI + "/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        deleteRoomReservationAfterTest(rid);
    }

    @Test
    public void updateRoomReservationWithoutTokenShouldReturnUnauthorized() {
        Long rid = createAndSaveRoomReservationBeforeTest();

        given()
                .pathParam("id", 1)
                .pathParam("rid", rid)
                .when()
                .put(URI + "/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

        deleteRoomReservationAfterTest(rid);
    }

    @Test
    public void updateRoomReservationWithClientTokenShouldReturnForbidden() {
        Long rid = createAndSaveRoomReservationBeforeTest();

        RoomReservationUpdate updatedRoomReservation = roomReservationUpdater.updateRoomReservation();

        getClientWithClientToken()
                .pathParam("id", 1)
                .pathParam("rid", rid)
                .contentType(ContentType.JSON)
                .body(updatedRoomReservation)
                .when()
                .put(URI + "/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());

        deleteRoomReservationAfterTest(rid);
    }

    @Test
    public void updateRoomReservationWithClientTokenAndInvalidDataShouldReturnForbidden() {
        Long rid = createAndSaveRoomReservationBeforeTest();

        RoomReservationUpdate updatedRoomReservation = roomReservationUpdater.updateRoomReservationBadRequest();

        getClientWithClientToken()
                .pathParam("id", 1)
                .pathParam("rid", rid)
                .contentType(ContentType.JSON)
                .body(updatedRoomReservation)
                .when()
                .put(URI + "/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());

        deleteRoomReservationAfterTest(rid);
    }

    private Long createAndSaveRoomReservationBeforeTest() {
        RoomReservationSave roomReservationSave = roomReservationCreator.createRoomReservation();

        RoomReservationSaveResponse roomReservationSaveResponse =
                getClientWithAdminToken()
                        .pathParam("id", 1)
                        .contentType(ContentType.JSON)
                        .body(roomReservationSave)
                        .when()
                        .post(URI)
                        .then()
                        .assertThat()
                        .contentType(ContentType.JSON)
                        .extract()
                        .as(RoomReservationSaveResponse.class);

        return roomReservationSaveResponse.getId();
    }

    private void deleteRoomReservationAfterTest(Long rid) {
        getClientWithAdminToken()
                .pathParam("id", 1)
                .pathParam("rid", rid)
                .when()
                .delete(URI + "/{rid}");
    }
}

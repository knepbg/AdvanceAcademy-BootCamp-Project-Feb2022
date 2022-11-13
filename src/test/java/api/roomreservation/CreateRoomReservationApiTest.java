package api.roomreservation;

import api.BaseApiTest;
import api.roomreservation.creator.RoomReservationCreator;
import com.jbcamp1.moonlighthotel.dto.roomreservation.request.RoomReservationSave;
import com.jbcamp1.moonlighthotel.dto.roomreservation.response.RoomReservationSaveResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class CreateRoomReservationApiTest extends BaseApiTest {

    private static final String URI = "/rooms/{id}/reservations";
    private final RoomReservationCreator roomReservationCreator = new RoomReservationCreator();

    @Test
    public void createRoomReservationWithAdminTokenShouldReturnOK() {
        RoomReservationSave roomReservation = roomReservationCreator.createRoomReservation();

        RoomReservationSaveResponse roomReservationSaveResponse =
                getClientWithAdminToken()
                        .pathParam("id", 1)
                        .contentType(ContentType.JSON)
                        .body(roomReservation)
                        .when()
                        .post(URI)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .contentType(ContentType.JSON)
                        .extract()
                        .as(RoomReservationSaveResponse.class);

        Long rid = roomReservationSaveResponse.getId();
        deleteRoomReservationAfterTest(rid);
    }

    @Test
    public void createRoomReservationWithClientTokenShouldReturnOK() {
        RoomReservationSave roomReservation = roomReservationCreator.createRoomReservation();

        RoomReservationSaveResponse roomReservationSaveResponse =
                getClientWithClientToken()
                        .pathParam("id", 1)
                        .contentType(ContentType.JSON)
                        .body(roomReservation)
                        .when()
                        .post(URI)
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .contentType(ContentType.JSON)
                        .extract()
                        .as(RoomReservationSaveResponse.class);

        Long rid = roomReservationSaveResponse.getId();
        deleteRoomReservationAfterTest(rid);
    }

    @Test
    public void createRoomReservationWithAdminTokenAndInvalidDataShouldReturnBadRequest() {
        RoomReservationSave roomReservation = roomReservationCreator.createRoomReservationBadRequest();

        getClientWithAdminToken()
                .pathParam("id", 1)
                .contentType(ContentType.JSON)
                .body(roomReservation)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createRoomReservationWithClientTokenAndInvalidDataShouldReturnBadRequest() {
        RoomReservationSave roomReservation = roomReservationCreator.createRoomReservationBadRequest();

        getClientWithClientToken()
                .pathParam("id", 1)
                .contentType(ContentType.JSON)
                .body(roomReservation)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createRoomReservationWithoutTokenShouldReturnUnauthorized() {
        given()
                .pathParam("id", 1)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private void deleteRoomReservationAfterTest(Long rid) {
        getClientWithAdminToken()
                .pathParam("id", 1)
                .pathParam("rid", rid)
                .when()
                .delete(URI + "/{rid}");
    }
}

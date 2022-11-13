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
public class DeleteRoomReservationApiTest extends BaseApiTest {

    private static final String URI = "/rooms/{id}/reservations";
    private final RoomReservationCreator roomReservationCreator = new RoomReservationCreator();

    @Test
    public void deleteRoomReservationByIdWithAdminTokenShouldReturnNoContent() {
        Long rid = createAndSaveRoomReservationBeforeTest();

        getClientWithAdminToken()
                .pathParam("id", 1)
                .pathParam("rid", rid)
                .when()
                .delete(URI + "/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deleteRoomReservationByIdWithoutTokenShouldReturnUnauthorized() {
        Long rid = createAndSaveRoomReservationBeforeTest();

        given()
                .pathParam("id", 1)
                .pathParam("rid", rid)
                .when()
                .delete(URI + "/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

        deleteRoomReservationAfterTest(rid);
    }

    @Test
    public void deleteRoomReservationByIdWithClientTokenShouldReturnForbidden() {
        Long rid = createAndSaveRoomReservationBeforeTest();

        getClientWithClientToken()
                .pathParam("id", 1)
                .pathParam("rid", rid)
                .when()
                .delete(URI + "/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());

        deleteRoomReservationAfterTest(rid);
    }

    @Test
    public void deleteRoomReservationByIdOfNonExistentRoomReservationWithAdminTokenShouldReturnNotFound() {
        Long rid = 6000L;

        getClientWithAdminToken()
                .pathParam("id", 1)
                .pathParam("rid", rid)
                .when()
                .delete(URI + "/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
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

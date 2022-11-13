package api.roomreservation;

import api.BaseApiTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class GetRoomReservationsApiTest extends BaseApiTest {

    private static final String URI = "/rooms/{id}/reservations";

    @Test
    public void getRoomReservationsWithAdminTokenShouldReturnOK() {
        getClientWithAdminToken()
                .pathParam("id", 1)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getRoomReservationsByInvalidRoomIdWithAdminTokenShouldReturnBadRequest() {
        getClientWithAdminToken()
                .pathParam("id", "a")
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getRoomReservationsByInvalidRoomIdWithClientTokenShouldReturnBadRequest() {
        getClientWithClientToken()
                .pathParam("id", "a")
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getRoomReservationsWithoutTokenShouldReturnUnauthorized() {
        given()
                .pathParam("id", 1)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void getRoomReservationsWithClientTokenShouldReturnForbidden() {
        getClientWithClientToken()
                .pathParam("id", 1)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}

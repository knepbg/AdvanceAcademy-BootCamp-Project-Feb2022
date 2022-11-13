package api.roomreservation;

import api.BaseApiTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class GetRoomReservationByIdApiTest extends BaseApiTest {

    private static final String URI = "/rooms/{id}/reservations/{rid}";

    @Test
    public void getRoomReservationByIdWithAdminTokenShouldReturnOK() {
        getClientWithAdminToken()
                .pathParam("id", 1)
                .pathParam("rid", 3)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getRoomReservationByInvalidIdWithAdminTokenShouldReturnBadRequest() {
        getClientWithAdminToken()
                .pathParam("id", 1)
                .pathParam("rid", "a")
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getRoomReservationByInvalidIdWithClientTokenShouldReturnBadRequest() {
        getClientWithClientToken()
                .pathParam("id", 1)
                .pathParam("rid", "a")
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getRoomReservationByIdWithoutTokenShouldReturnUnauthorized() {
        given()
                .pathParam("id", 1)
                .pathParam("rid", 1)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void getRoomReservationByIdWithClientTokenShouldReturnForbidden() {
        getClientWithClientToken()
                .pathParam("id", 1)
                .pathParam("rid", 3)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getRoomReservationByNonExistentIdWithAdminTokenShouldReturnNotFound() {
        getClientWithAdminToken()
                .pathParam("id", 1)
                .pathParam("rid", 1)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void getRoomReservationByNonExistentIdWithClientTokenShouldReturnForbidden() {
        getClientWithClientToken()
                .pathParam("id", 1)
                .pathParam("rid", 1)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}

package userApi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class UserGetReservationTest extends BaseApiTest {

    @Test
    public void getReservationByUserIdShouldReturnOk() {
        getClientWithAdminToken()
                .pathParam("uid", 3)
                .when()
                .get("users/{uid}/reservations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getReservationByUserIdShouldReturnForbidden() {
        getAdminWithClientToken()
                .pathParam("uid", 3)
                .when()
                .get("users/{uid}/reservations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getReservationByUserIdShouldReturnBadRequest() {
        getClientWithAdminToken()
                .pathParam("uid", "asdasd")
                .when()
                .get("users/{uid}/reservations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getReservationByUserIdShouldReturnNotFound() {
        getClientWithAdminToken()
                .pathParam("uid", 121236)
                .when()
                .get("users/{uid}/reservations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void getReservationByUserIdShouldReturnUnauthorized() {
        given()
                .pathParam("uid", 6)
                .when()
                .get("users/{uid}/reservations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void getReservationAndUserByIdShouldReturnOk() {
        getClientWithAdminToken()
                .pathParam("uid", 2)
                .pathParam("rid", 4)
                .when()
                .get("users/{uid}/reservations/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getReservationAndUserByIdShouldReturnForbidden() {
        getAdminWithClientToken()
                .pathParam("uid", 2)
                .pathParam("rid", 4)
                .when()
                .get("users/{uid}/reservations/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getReservationAndUserByIdShouldReturnNotFound() {
        getClientWithAdminToken()
                .pathParam("uid", 3)
                .pathParam("rid", 4)
                .when()
                .get("users/{uid}/reservations/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void getReservationAndUserByIdShouldReturnUnAuthorized() {
        given()
                .pathParam("uid", 2)
                .pathParam("rid", 4)
                .when()
                .get("users/{uid}/reservations/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void getReservationAndUserByIdShouldReturnBadRequest() {
        getClientWithAdminToken()
                .pathParam("uid", "asd")
                .pathParam("rid", 4)
                .when()
                .get("users/{uid}/reservations/{rid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}

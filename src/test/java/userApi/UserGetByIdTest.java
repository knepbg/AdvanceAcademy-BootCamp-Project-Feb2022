package userApi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class UserGetByIdTest extends BaseApiTest {


    @Test
    public void getUserByIdReturnsUserShouldReturnOk() {
        getClientWithAdminToken()
                .pathParam("id", 5)
                .when()
                .get("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getUserByIdShouldReturnUnauthorized() {
        given()
                .pathParam("id", 5)
                .when()
                .get("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void getUserByIdShouldReturnForbidden() {
        getAdminWithClientToken()
                .pathParam("id", 5)
                .when()
                .get("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getUserByIdShouldReturnNotFound() {
        getClientWithAdminToken()
                .pathParam("id", 1123)
                .when()
                .get("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void getUserByIdShouldReturnBadRequest() {
        getClientWithAdminToken()
                .pathParam("id", "adasd")
                .when()
                .get("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}

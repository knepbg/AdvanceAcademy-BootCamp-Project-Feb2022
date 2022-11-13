package userApi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(JUnit4.class)
public class UserDeleteTest extends BaseApiTest {

    @Test
    public void deleteUserShouldReturnNotFound() {
        getClientWithAdminToken()
                .pathParam("id", "6123")
                .when()
                .delete("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", notNullValue());
    }

    @Test
    public void deleteUserShouldReturnNoContent() {
        getClientWithAdminToken()
                .pathParam("id", "35")
                .when()
                .delete("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deleteUserShouldReturnUnauthorized() {
        given()
                .pathParam("id", "35")
                .when()
                .delete("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("message", notNullValue());
    }
}

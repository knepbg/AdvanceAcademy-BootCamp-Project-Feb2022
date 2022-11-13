package userApi;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class UserUpdateTest extends BaseApiTest {

    @Test
    public void updateUserShouldReturnUnauthorized() {
        given()
                .pathParam("id", "33")
                .when()
                .put("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void updateUserShouldReturnOk() {
        String user = "{\"email\": \"daniel.ivanov@gmail.com\", \"password\": \"123456\" " +
                ", \"name\":\"Daniel\" , \"surname\":\"Ivanov\" " +
                ", \"phone\":\"0830303030\" , \"roles\": [\"client\"]} ";

        getClientWithAdminToken()
                .pathParam("id", "35")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void updateUserShouldReturnNotFound() {
        String user = "{\"email\": \"daniel.ivanov@gmail.com\", \"password\": \"123456\" " +
                ", \"name\":\"Daniel\" , \"surname\":\"Ivanov\" " +
                ", \"phone\":\"0830303030\" , \"roles\": [\"client\"]} ";

        getClientWithAdminToken()
                .pathParam("id", "33123")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void updateUserShouldReturnBadRequest() {
        String user = "{\"email\": \"daniel.ivanov@gmail.com\", \"password\": \"\" " +
                ", \"name\":\"\" , \"surname\":\"\" " +
                ", \"phone\":\"0830303030\" , \"roles\": [\"client\"]} ";

        getClientWithAdminToken()
                .pathParam("id", "33")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUserWithClientTokenShouldReturnForbidden() {
        String user = "{\"email\" : \"daniela.gelova@gmail.com\", \"password\": \"123456\" " +
                ", \"name\":\"Daniela\" , \"surname\":\"Gelova\" " +
                ", \"phone\":\"0898777334\" , \"roles\": [\"client\"]} ";

        getAdminWithClientToken()
                .pathParam("id", "35")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("/users/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}

package userApi;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(JUnit4.class)
public class UserTokenTest {

    @Test
    public void getUserTokenShouldReturnOk() {
        String user = "{\"email\" : \"yovcheva@moonlight_hotel.com\", \"password\" : \"123456\" }";

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users/token")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("token", notNullValue(),
                        "user.email", is("yovcheva@moonlight_hotel.com"),
                        "user.name", notNullValue(),
                        "user.surname", notNullValue(),
                        "user.phone", notNullValue(),
                        "user.roles", notNullValue(),
                        "user.created", notNullValue()
                );
    }
    @Test
    public void getUserTokenShouldReturnBadRequest() {
        String user = "{\"email\" : \"yovcheva@moonlight_hotel.com\", \"password\" : \"\" }";

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users/token")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}

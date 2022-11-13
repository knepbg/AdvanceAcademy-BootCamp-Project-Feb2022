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
public class UserCreateTest {

    @Test
    public void createUserShouldReturnCreated() {
        String user = "{\"email\" : \"daniela.gelova@gmail.com\", \"password\": \"123456\" " +
                ", \"name\":\"Daniela\" , \"surname\":\"Petrova\" " +
                ", \"phone\":\"0898777334\" , \"roles\": [\"client\"]} ";

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("email", is("daniela.gelova@gmail.com"),
                        "name", is("Daniela"),
                        "surname", is("Petrova"),
                        "phone", is("0898777334"),
                        "roles", notNullValue(),
                        "created", notNullValue()
                );
    }

    @Test
    public void createUserShouldReturnBadRequest() {
        String user = "{\"email\": \"\", \"password\": \"123456\" " +
                ", \"name\":\"Daniela\" , \"surname\":\"Petrova\" " +
                ", \"phone\":\"0898777334\" , \"roles\": [\"client\"]} ";

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}

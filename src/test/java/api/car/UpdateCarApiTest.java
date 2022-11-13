package api.car;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;
import userApi.BaseApiTest;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class UpdateCarApiTest extends BaseApiTest {
    private static final String URI = "/cars";

    @Test
    public void updateCarReturnsUnauthorized() {
        given()
                .pathParam("id", "13")
                .when()
                .put(URI +"/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void updateCarShouldReturnOk() {
        String car = "{\"category\": \"1\", \"brand\": \"Ferrari\" " +
                ", \"model\":\"SF90\" , \"image\":\"https://autobild.bg/wp-content/uploads/2020/11/Ferrari-SF90-Spider-1200x800-b8fe307be94fcb36.jpg\" " +
                ", \"images\": [\"https://autobild.bg/wp-content/uploads/2020/11/Ferrari-SF90-Spider-1200x800-b8fe307be94fcb36.jpg\"], " +
                "\"year\":\"2016\"} ";

        getClientWithAdminToken()
                .pathParam("id", "12")
                .contentType(ContentType.JSON)
                .body(car)
                .when()
                .put(URI +"/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    public void updateCarShouldReturnForbidden() {
        String car = "{\"category\": \"1\", \"brand\": \"Ferrari\" " +
                ", \"model\":\"SF90\" , \"image\":\"https://autobild.bg/wp-content/uploads/2020/11/Ferrari-SF90-Spider-1200x800-b8fe307be94fcb36.jpg\" " +
                ", \"images\": [\"https://autobild.bg/wp-content/uploads/2020/11/Ferrari-SF90-Spider-1200x800-b8fe307be94fcb36.jpg\"], " +
                "\"year\":\"2017\"} ";

        getAdminWithClientToken()
                .pathParam("id", "12")
                .contentType(ContentType.JSON)
                .body(car)
                .when()
                .put(URI +"/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
    @Test
    public void updateCarShouldReturnBadRequest() {
        String car = "{\"category\": \"1\", \"brand\": \"\" " +
                ", \"model\":\"\" , \"image\":\"https://autobild.bg/wp-content/uploads/2020/11/Ferrari-SF90-Spider-1200x800-b8fe307be94fcb36.jpg\" " +
                ", \"images\": [\"https://autobild.bg/wp-content/uploads/2020/11/Ferrari-SF90-Spider-1200x800-b8fe307be94fcb36.jpg\"], " +
                "\"year\":\"2017\"} ";

        getClientWithAdminToken()
                .pathParam("id", "12")
                .contentType(ContentType.JSON)
                .body(car)
                .when()
                .put(URI +"/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    public void updateCarShouldReturnNotFound() {
        String car = "{\"category\": \"1\", \"brand\": \"Audi\" " +
                ", \"model\":\"R8\" , \"image\":\"http://image.jpeg\" " +
                ", \"images\": [\"http://image.jpeg\"], " +
                "\"year\":\"2021\"} ";

        getClientWithAdminToken()
                .pathParam("id", "1232")
                .contentType(ContentType.JSON)
                .body(car)
                .when()
                .put(URI +"/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}

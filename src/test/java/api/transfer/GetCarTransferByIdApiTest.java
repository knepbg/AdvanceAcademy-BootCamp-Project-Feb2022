package api.transfer;

import api.BaseApiTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class GetCarTransferByIdApiTest extends BaseApiTest {

    private static final String URI = "/cars/{id}/transfers/{tid}";

    @Test
    public void getCarTransferByIdWithAdminTokenShouldReturnOK() {
        getClientWithAdminToken()
                .pathParam("id", 1)
                .pathParam("tid", 3)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getCarTransferByInvalidIdWithAdminTokenShouldReturnBadRequest() {
        getClientWithAdminToken()
                .pathParam("id", 1)
                .pathParam("tid", "a")
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getCarTransferByInvalidIdWithClientTokenShouldReturnBadRequest() {
        getClientWithClientToken()
                .pathParam("id", 1)
                .pathParam("tid", "a")
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getCarTransferByIdWithoutTokenShouldReturnUnauthorized() {
        given()
                .pathParam("id", 1)
                .pathParam("tid", 1)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void getCarTransferByIdWithClientTokenShouldReturnForbidden() {
        getClientWithClientToken()
                .pathParam("id", 1)
                .pathParam("tid", 3)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getCarTransferByNonExistentIdWithClientTokenShouldReturnForbidden() {
        getClientWithClientToken()
                .pathParam("id", 1)
                .pathParam("tid", 6000)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getCarTransferByNonExistentIdWithAdminTokenShouldReturnNotFound() {
        getClientWithAdminToken()
                .pathParam("id", 1)
                .pathParam("tid", 6000)
                .when()
                .get(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}

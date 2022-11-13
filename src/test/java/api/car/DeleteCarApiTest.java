package api.car;

import api.BaseApiTest;
import api.car.creator.CarCreator;
import com.jbcamp1.moonlighthotel.dto.car.request.CarRequest;
import com.jbcamp1.moonlighthotel.dto.car.response.CarResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
public class DeleteCarApiTest extends BaseApiTest {

    private static final String URI = "/cars";
    private final CarCreator carCreator = new CarCreator();

    @Test
    public void deleteCarByIdWithAdminTokenShouldReturnNoContent() {
        Long id = createAndSaveCarBeforeTest();

        getClientWithAdminToken()
                .pathParam("id", id)
                .when()
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deleteCarByIdWithoutTokenShouldReturnUnauthorized() {
        Long id = createAndSaveCarBeforeTest();

        given()
                .pathParam("id", id)
                .when()
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

        deleteCarAfterTest(id);
    }

    @Test
    public void deleteCarByIdWithClientTokenShouldReturnForbidden() {
        Long id = createAndSaveCarBeforeTest();

        getClientWithClientToken()
                .pathParam("id", id)
                .when()
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());

        deleteCarAfterTest(id);
    }

    @Test
    public void deleteNonExistentCarRecordWithAdminTokenShouldReturnNotFound() {
        Long id = 6000L;

        getClientWithAdminToken()
                .pathParam("id", id)
                .when()
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .contentType(ContentType.JSON);
    }

    @Test
    public void deleteNonExistentCarRecordWithClientTokenShouldReturnForbidden() {
        Long id = 6000L;

        getClientWithClientToken()
                .pathParam("id", id)
                .when()
                .delete(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .contentType(ContentType.JSON);
    }

    private Long createAndSaveCarBeforeTest() {
        CarRequest carRequest = carCreator.createCar();

        CarResponse carResponse =
                getClientWithAdminToken()
                        .contentType(ContentType.JSON)
                        .body(carRequest)
                        .when()
                        .post(URI)
                        .then()
                        .assertThat()
                        .contentType(ContentType.JSON)
                        .extract()
                        .as(CarResponse.class);

        return carResponse.getId();
    }

    private void deleteCarAfterTest(Long id) {
        getClientWithAdminToken()
                .pathParam("id", id)
                .when()
                .delete(URI + "/{id}");
    }
}

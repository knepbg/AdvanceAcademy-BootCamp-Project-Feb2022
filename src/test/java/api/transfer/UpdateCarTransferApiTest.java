package api.transfer;

import api.BaseApiTest;
import api.car.creator.CarCreator;
import api.transfer.creator.CarTransferCreator;
import api.transfer.updater.CarTransferUpdater;
import com.jbcamp1.moonlighthotel.dto.car.request.CarRequest;
import com.jbcamp1.moonlighthotel.dto.car.response.CarResponse;
import com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferRequest.CarTransferRequest;
import com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferRequest.CarTransferRequestUpdate;
import com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferResponse.CarTransferResponse;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(JUnit4.class)
public class UpdateCarTransferApiTest extends BaseApiTest {

    private static final String CARS_URI = "/cars";
    private static final String TRANSFERS_URI = "/cars/{id}/transfers";

    private final CarCreator carCreator = new CarCreator();
    private final CarTransferCreator carTransferCreator = new CarTransferCreator();
    private final CarTransferUpdater carTransferUpdater = new CarTransferUpdater();

    @Test
    public void updateCarTransferWithAdminTokenShouldReturnOK() {
        Long id = createAndSaveCarBeforeTest();
        Long tid = createAndSaveCarTransferBeforeTest(id);

        CarTransferRequestUpdate carTransferRequestUpdate = carTransferUpdater.updateCarTransfer();

        CarTransferResponse carTransferResponse =
                getClientWithAdminToken()
                        .pathParam("id", id)
                        .pathParam("tid", tid)
                        .contentType(ContentType.JSON)
                        .body(carTransferRequestUpdate)
                        .when()
                        .put(TRANSFERS_URI + "/{tid}")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .contentType(ContentType.JSON)
                        .extract()
                        .as(CarTransferResponse.class);

        Long updatedTransferId = carTransferResponse.getId();
        deleteCarTransferAfterTest(id, updatedTransferId);
        deleteCarAfterTest(id);
    }

    @Test
    public void updateCarTransferWithAdminTokenAndInvalidDataShouldReturnBadRequest() {
        Long id = createAndSaveCarBeforeTest();
        Long tid = createAndSaveCarTransferBeforeTest(id);

        CarTransferRequestUpdate carTransferRequestUpdate = carTransferUpdater.updateCarTransferBadRequest();

        getClientWithAdminToken()
                .pathParam("id", id)
                .pathParam("tid", tid)
                .contentType(ContentType.JSON)
                .body(carTransferRequestUpdate)
                .when()
                .put(TRANSFERS_URI + "/{tid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("message", is(notNullValue()));

        deleteCarTransferAfterTest(id, tid);
        deleteCarAfterTest(id);
    }

    @Test
    public void updateCarTransferWithoutTokenShouldReturnUnauthorized() {
        Long id = 1L;
        Long tid = 1L;

        given()
                .pathParam("id", id)
                .pathParam("tid", tid)
                .when()
                .put(TRANSFERS_URI + "/{tid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .contentType(ContentType.JSON)
                .body("message", is(notNullValue()))
                .body("message", is("Unauthorized access. Please login to continue."));
    }

    @Test
    public void updateCarTransferWithClientTokenShouldReturnForbidden() {
        Long id = createAndSaveCarBeforeTest();
        Long tid = 1L;

        CarTransferRequestUpdate carTransferRequestUpdate = carTransferUpdater.updateCarTransfer();

        getClientWithClientToken()
                .pathParam("id", id)
                .pathParam("tid", tid)
                .contentType(ContentType.JSON)
                .body(carTransferRequestUpdate)
                .when()
                .put(TRANSFERS_URI + "/{tid}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .contentType(ContentType.JSON)
                .body("message", is(notNullValue()))
                .body("message", is("Access forbidden. You do not have permission to access this resource."));

        deleteCarAfterTest(id);
    }

    private Long createAndSaveCarBeforeTest() {
        CarRequest carRequest = carCreator.createCar();

        CarResponse carResponse =
                getClientWithAdminToken()
                        .contentType(ContentType.JSON)
                        .body(carRequest)
                        .when()
                        .post(CARS_URI)
                        .then()
                        .assertThat()
                        .contentType(ContentType.JSON)
                        .extract()
                        .as(CarResponse.class);

        return carResponse.getId();
    }

    private Long createAndSaveCarTransferBeforeTest(Long id) {
        CarTransferRequest carTransferRequest = carTransferCreator.createCarTransfer();

        CarTransferResponse carTransferResponse =
                getClientWithAdminToken()
                        .pathParam("id", id)
                        .contentType(ContentType.JSON)
                        .body(carTransferRequest)
                        .when()
                        .post(TRANSFERS_URI)
                        .then()
                        .extract()
                        .as(CarTransferResponse.class);

        return carTransferResponse.getId();
    }

    private void deleteCarAfterTest(Long id) {
        getClientWithAdminToken()
                .pathParam("id", id)
                .delete(CARS_URI + "/{id}");
    }

    private void deleteCarTransferAfterTest(Long id, Long tid) {
        getClientWithAdminToken()
                .pathParam("id", id)
                .pathParam("tid", tid)
                .delete(TRANSFERS_URI + "/{tid}");
    }
}

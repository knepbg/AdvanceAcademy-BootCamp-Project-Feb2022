package apiTests.RoomApiTest;

import apiTests.BaseApiTest;
import apiTests.FileReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbcamp1.moonlighthotel.dto.room.request.RoomRequest;
import com.jbcamp1.moonlighthotel.dto.room.response.RoomResponse;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class RoomApiUpdateTest extends BaseApiTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FileReader fileReader = new FileReader();

    private Long rid;
    private final String URI = "http://localhost:8080/rooms";

    @Before
    public void saveRoomInToDataBase() throws Exception {
        String content = fileReader.readFile("classpath:roomRequestSave.json");

        RoomResponse roomResponse = getClientWithAdminToken()
                .body(content)
                .contentType(ContentType.JSON)
                .when()
                .post(URI)
                .then()
                .extract()
                .as(RoomResponse.class);

        rid = roomResponse.getId();

    }

    @Test
    public void updateRoomShouldReturnOk() throws Exception {
        String content = fileReader.readFile("classpath:roomRequestUpdate.json");
        RoomRequest roomRequestUpdate = objectMapper.readValue(content, RoomRequest.class);

        getClientWithAdminToken()
                .body(roomRequestUpdate)
                .pathParam("id", rid)
                .contentType(ContentType.JSON)
                .when()
                .put(URI + "/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
        // and another assertion can be written also ...
    }

    @After
    public void deleteRoom() throws Exception {

        getClientWithAdminToken()
                .pathParam("id", rid)
                .contentType(ContentType.JSON)
                .when()
                .delete(URI + "/{id}");
    }

}

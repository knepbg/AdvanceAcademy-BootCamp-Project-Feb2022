package apiTests.RoomApiTest;

import apiTests.BaseApiTest;
import apiTests.FileReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbcamp1.moonlighthotel.dto.room.request.RoomRequest;
import com.jbcamp1.moonlighthotel.dto.room.response.RoomResponse;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
public class RoomApiSaveTest extends BaseApiTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FileReader fileReader = new FileReader();

    private Long rid;
    private final String URI = "http://localhost:8080/rooms";

    @Test
    public void saveRoomShouldReturnCreated() throws Exception {

        String content = fileReader.readFile("classpath:roomRequestSave.json");
        RoomRequest roomRequestSave = objectMapper.readValue(content, RoomRequest.class);

        RoomResponse roomResponse = getClientWithAdminToken()
                .body(roomRequestSave)
                .contentType(ContentType.JSON)
                .when()
                .post(URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(RoomResponse.class);
        // and another assertion can be written also ...
        rid = roomResponse.getId();
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

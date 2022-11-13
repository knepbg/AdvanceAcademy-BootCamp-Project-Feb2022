package userApi;

import com.jbcamp1.moonlighthotel.model.User;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseApiTest {
    private JwtGenerator jwtGenerator = new JwtGenerator();

    public RequestSpecification getClientWithAdminToken() {
        String tokenAdmin = jwtGenerator.generateTokenAdmin();
        return given()
                .header("Authorization" , "Bearer " + tokenAdmin);

    }
    public RequestSpecification getAdminWithClientToken() {
        String tokenClient = jwtGenerator.generateTokenClient();
        return given()
                .header("Authorization" , "Bearer " + tokenClient );
    }
}

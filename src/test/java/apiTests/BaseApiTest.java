package apiTests;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseApiTest {

    private final JwtGenerator jwtGenerator = new JwtGenerator();

    public RequestSpecification getClientWithAdminToken() {
        String tokenAdmin = jwtGenerator.generateTokenAdmin();
        return given()
                .header("Authorization", "Bearer " + tokenAdmin);

    }
}
package apiTests;

import com.jbcamp1.moonlighthotel.security.jwt.JwtRequest;
import com.jbcamp1.moonlighthotel.security.jwt.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class JwtGenerator {

    private static final String TOKEN_URL = "http://localhost:8080/users/token";

    private static final JwtRequest ADMIN_USER = new JwtRequest("yovcheva@moonlight_hotel.com", "123456");
    private static final JwtRequest CLIENT_USER = new JwtRequest("p.ivanov@abv.bg", "123456");

    private RestTemplate restTemplate = new RestTemplate();

    public String generateTokenAdmin() {
        ResponseEntity<JwtResponse> jwtResponseResponseEntity
                = restTemplate.postForEntity(TOKEN_URL, ADMIN_USER, JwtResponse.class);

        return jwtResponseResponseEntity.getBody().getToken();
    }
}

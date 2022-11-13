package com.jbcamp1.moonlighthotel.security.jwt;

import com.jbcamp1.moonlighthotel.dto.user.response.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JwtResponse {

    @Schema(description = "Authentication Token")
    private String token;

    private UserResponse user;
}

package com.jbcamp1.moonlighthotel.security.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JwtRequest {

    @Email
    @NotBlank
    @Schema(description = "Auth Email")
    private String email;

    @NotBlank
    @Length(min = 6)
    @Schema(description = "Auth Password")
    private String password;
}

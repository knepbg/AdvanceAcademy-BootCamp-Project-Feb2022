package com.jbcamp1.moonlighthotel.dto.user.request;

import com.jbcamp1.moonlighthotel.model.Role;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserRequestCreate {

    @Length(min = 2, max = 255)
    @NotBlank
    @Email(message = "Invalid email format")
    @Schema(description = "Email for authentication and future notifications")
    private String email;

    @Length(min = 6, max = 255)
    @NotBlank(message = "enter a password")
    @Schema(description = "Password for credentials")
    private String password;

    @NotBlank
    @Length(min = 2, max = 255)
    @Schema(description = "First name")
    private String name;

    @NotBlank
    @Length(min = 2, max = 255)
    @Schema(description = "Last name")
    private String surname;

    @NotBlank
    @Length(min = 1, max = 15)
    private String phone;

    @ArraySchema(arraySchema = @Schema(implementation = Role.class, description = "Roles of the User. Ignored if used for new registrations (clients).",
            example = "[\"admin\"]"))
    private Set<String> roles;
}

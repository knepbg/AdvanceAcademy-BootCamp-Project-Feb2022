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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserRequestUpdate {

    @NotBlank
    @Email
    @Schema(description = "Email for authentication and future notifications")
    private String email;

    @NotBlank(message = "enter a password")
    @Length(min = 6, max = 255)
    @Schema(description = "Password for credentials")
    private String password;

    @Length(min = 2, max = 255)
    @NotBlank
    @Schema(description = "First name")
    private String name;

    @Length(min = 2, max = 255)
    @Schema(description = "Last name")
    private String surname;

    @NotBlank
    @Size(min = 1, max = 15)
    private String phone;

    @NotEmpty
    @ArraySchema(arraySchema = @Schema(implementation = Role.class, description = "Roles of the User. Ignored if used for new registrations (clients).",
            example = "[\"admin\"]"))
    private Set<String> roles;
}


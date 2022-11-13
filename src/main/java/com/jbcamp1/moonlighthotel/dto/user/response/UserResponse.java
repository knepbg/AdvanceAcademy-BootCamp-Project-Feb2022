package com.jbcamp1.moonlighthotel.dto.user.response;

import com.jbcamp1.moonlighthotel.model.Role;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserResponse {

    private Long id;

    @Schema(description = "Email for authentication and future notifications")
    private String email;

    @Schema(description = "First name")
    private String name;

    @Schema(description = "Last name")
    private String surname;

    @Schema(description = "Phone Number")
    private String phone;

    @ArraySchema(arraySchema = @Schema(implementation = Role.class,
            description = "Roles of the User. Ignored if used for new registrations (clients).",
            example = "[\"admin\"]"))
    private Set<String> roles;

    @Schema(description = "Registered Date. ISO-8601 in UTC string")
    private String created;
}

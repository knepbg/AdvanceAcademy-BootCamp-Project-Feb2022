package com.jbcamp1.moonlighthotel.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserRequestReset {

    // Using : @PostMapping /user/reset --> @RequestBody UserRequestReset

    private String email;

    private String password;

    private String token;
}

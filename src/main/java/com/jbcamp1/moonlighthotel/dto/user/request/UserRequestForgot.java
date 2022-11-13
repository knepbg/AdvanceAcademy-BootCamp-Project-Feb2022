package com.jbcamp1.moonlighthotel.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserRequestForgot {

    // Using : @PostMapping /user/forgot --> @RequestBody UserRequestForgot

    private String email;
}

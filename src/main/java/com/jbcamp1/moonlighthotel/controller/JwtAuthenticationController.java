package com.jbcamp1.moonlighthotel.controller;

import com.jbcamp1.moonlighthotel.dto.user.response.UserResponse;
import com.jbcamp1.moonlighthotel.exception.AuthenticationFailException;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelValidation;
import com.jbcamp1.moonlighthotel.mapper.UserMapper;
import com.jbcamp1.moonlighthotel.model.User;
import com.jbcamp1.moonlighthotel.security.JwtUserDetailsService;
import com.jbcamp1.moonlighthotel.security.jwt.JwtRequest;
import com.jbcamp1.moonlighthotel.security.jwt.JwtResponse;
import com.jbcamp1.moonlighthotel.security.jwt.JwtUtil;
import com.jbcamp1.moonlighthotel.service.UserService;
import com.jbcamp1.moonlighthotel.swagger.SwaggerConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Tag(name = SwaggerConfiguration.USER_TAG)
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/users/token", method = RequestMethod.POST)
    @Operation(summary = "Obtain a JWT Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelValidation.class))})})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        if (authenticationRequest.getEmail() == null) {
            throw new AuthenticationFailException("Incorrect email or password", "email or password");
        } else if (authenticationRequest.getPassword() == null) {
            throw new AuthenticationFailException("Incorrect email or password", "email or password");
        } else {
            try {
                User user = userService.findByEmail(authenticationRequest.getEmail());
                Boolean doPasswordsMatch = doPasswordsMatch(authenticationRequest.getPassword(), user.getPassword());
                if (!doPasswordsMatch) {
                    throw new AuthenticationFailException("Incorrect email or password", "email or password");
                }
            } catch (RecordNotFoundException e) {
                throw new AuthenticationFailException("Incorrect email or password", "email or password");
            }
            User user = userService.findByEmail(authenticationRequest.getEmail());
            authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(authenticationRequest.getEmail());

            final String token = jwtUtil.generateToken(userDetails);

            final UserResponse userResponse = userMapper.toUserResponse(user);

            return ResponseEntity.ok(new JwtResponse(token, userResponse));
        }
    }

    public Boolean doPasswordsMatch(String password, String encodedPassword) {
        return bCryptPasswordEncoder.matches(password, encodedPassword);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

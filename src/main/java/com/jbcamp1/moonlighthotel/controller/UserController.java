package com.jbcamp1.moonlighthotel.controller;

import com.jbcamp1.moonlighthotel.dto.user.request.UserRequestCreate;
import com.jbcamp1.moonlighthotel.dto.user.request.UserRequestUpdate;
import com.jbcamp1.moonlighthotel.dto.user.response.UserResponse;
import com.jbcamp1.moonlighthotel.dto.user.response.UserRoomReservationResponse;
import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelDuplicate;
import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelValidation;
import com.jbcamp1.moonlighthotel.mapper.UserMapper;
import com.jbcamp1.moonlighthotel.model.RoomReservation;
import com.jbcamp1.moonlighthotel.model.User;
import com.jbcamp1.moonlighthotel.service.RoomReservationService;
import com.jbcamp1.moonlighthotel.service.UserService;
import com.jbcamp1.moonlighthotel.swagger.SwaggerConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
@Tag(name = SwaggerConfiguration.USER_TAG)
@CrossOrigin
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private final RoomReservationService roomReservationService;

    @PostMapping
    @Operation(summary = "Create a new user (as admin) or register a client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelValidation.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<UserResponse> save(@RequestBody @Valid UserRequestCreate userRequestCreate,
                                             HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            User user = userMapper.toUserFromAdminCreate(userRequestCreate);
            User savedUser = userService.saveByAdmin(user);
            UserResponse userResponse = userMapper.toUserResponse(savedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

        } else {
            User user = userMapper.toUserFromClientCreate(userRequestCreate);
            User savedUser = userService.save(user);
            UserResponse userResponse = userMapper.toUserResponse(savedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get users List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<List<UserResponse>> findAll() {
        List<User> users = userService.findAll();
        List<UserResponse> userResponses = users.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userResponses);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get User by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<UserResponse> findById(@Parameter(description = "User ID", content = @Content(
            schema = @Schema(type = "integer", format = ""))) @PathVariable Long id) {
        User foundUser = userService.findById(id);
        UserResponse userResponse = userMapper.toUserResponse(foundUser);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a User by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelValidation.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserRequestUpdate userRequestUpdate,
                                               @Parameter(description = "User ID", content = @Content(
                                                       schema = @Schema(type = "integer", format = "")))
                                               @PathVariable Long id) {
        User userForUpdate = userMapper.toUser(userRequestUpdate);
        User updatedUser = userService.update(userForUpdate, id);
        UserResponse userResponse = userMapper.toUserResponse(updatedUser);
        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{uid}/reservations")
    public ResponseEntity<List<UserRoomReservationResponse>> findReservationsByUserId(@PathVariable Long uid) {

        Set<RoomReservation> reservationsUser = roomReservationService.findAllById(uid);

        List<UserRoomReservationResponse> userRoomReservationResponse = reservationsUser
                .stream()
                .map(userMapper::toUserRoomReservationsResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userRoomReservationResponse);
    }

    @GetMapping(value = "{uid}/reservations/{rid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserRoomReservationResponse> findReservation(@PathVariable Long uid,
                                                                       @PathVariable Long rid) {
        RoomReservation foundReservation = roomReservationService.findRoomReservationForUser(uid, rid);
        UserRoomReservationResponse userRoomReservationResponse = userMapper.toUserRoomReservationsResponse(foundReservation);

        return ResponseEntity.ok(userRoomReservationResponse);
    }

    @DeleteMapping(value = "/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Remove a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content",
                    content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<HttpStatus> deleteById(@Parameter(description = "User ID", content = @Content(
            schema = @Schema(type = "integer", format = ""))) @PathVariable Long id) {

        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/reservations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserRoomReservationResponse>> findAllRoomReservation() {

        List<RoomReservation> roomReservations = roomReservationService.findAllRoomReservation();

        List<UserRoomReservationResponse> userReservationResponse = roomReservations.stream()
                .map(userMapper::toUserRoomReservationsResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userReservationResponse);
    }
}

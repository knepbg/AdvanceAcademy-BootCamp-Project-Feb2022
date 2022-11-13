package com.jbcamp1.moonlighthotel.controller;

import com.jbcamp1.moonlighthotel.converter.RoomConverter;
import com.jbcamp1.moonlighthotel.dto.room.request.RoomRequest;
import com.jbcamp1.moonlighthotel.dto.room.response.RoomResponse;
import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelDuplicate;
import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelValidation;
import com.jbcamp1.moonlighthotel.filter.RoomFilter;
import com.jbcamp1.moonlighthotel.model.Room;
import com.jbcamp1.moonlighthotel.service.RoomService;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rooms")
@AllArgsConstructor
@Tag(name = SwaggerConfiguration.ROOM_TAG)
public class RoomController {

    @Autowired
    private final RoomService roomService;

    @Autowired
    private final RoomConverter roomConverter;

    @GetMapping
    @Operation(summary = "Get available rooms")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = RoomFilter.class))})
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = RoomResponse.class)))})
    public ResponseEntity<List<RoomResponse>> findAll(@Parameter(hidden = true) RoomFilter roomFilter) {
        List<Room> rooms = roomService.checkForQueryParameters(roomFilter);
        List<RoomResponse> roomResponseList = rooms.stream()
                .map(roomConverter::toRoomResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomResponseList);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a new room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelValidation.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<RoomResponse> save(@RequestBody @Valid RoomRequest roomRequestSave) {
        Room room = roomConverter.toRoom(roomRequestSave);
        Room savedRoom = roomService.save(room);
        RoomResponse roomResponseSave = roomConverter.toRoomResponse(savedRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomResponseSave);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a room by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponse.class))}),
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
    public ResponseEntity<RoomResponse> update(@RequestBody @Valid RoomRequest roomRequestUpdate,
                                               @Parameter(description = "Room ID",
                                                       content = @Content(schema = @Schema(type = "integer", format = "")))
                                               @PathVariable Long id) {

        Room roomForUpdate = roomConverter.toRoom(roomRequestUpdate);
        Room updatedRoom = roomService.update(roomForUpdate, id);
        RoomResponse roomResponse = roomConverter.toRoomResponse(updatedRoom);
        return ResponseEntity.ok(roomResponse);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Show a room by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelValidation.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<RoomResponse> findById(@Parameter(description = "Room ID", content = @Content(
            schema = @Schema(type = "integer", format = ""))) @PathVariable Long id) {
        return ResponseEntity.ok(roomConverter.toRoomResponse(roomService.findById(id)));
    }

    @DeleteMapping(value = "/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Remove a room by ID")
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
    public ResponseEntity<HttpStatus> deleteById(@Parameter(description = "Room ID", content = @Content(
            schema = @Schema(type = "integer", format = ""))) @PathVariable Long id) {

        roomService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
package com.jbcamp1.moonlighthotel.controller;

import com.jbcamp1.moonlighthotel.converter.RoomReservationConverter;
import com.jbcamp1.moonlighthotel.dto.roomreservation.request.RoomReservationSave;
import com.jbcamp1.moonlighthotel.dto.roomreservation.request.RoomReservationUpdate;
import com.jbcamp1.moonlighthotel.dto.roomreservation.response.RoomReservationResponse;
import com.jbcamp1.moonlighthotel.dto.roomreservation.response.RoomReservationSaveResponse;
import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelDuplicate;
import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelValidation;
import com.jbcamp1.moonlighthotel.model.RoomReservation;
import com.jbcamp1.moonlighthotel.service.RoomReservationService;
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
public class RoomReservationController {

    @Autowired
    private final RoomReservationService roomReservationService;

    @Autowired
    private final RoomReservationConverter roomReservationConverter;

    @PostMapping(value = "/{id}/summarize")
    @Operation(summary = "Calculate reservation info for room ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomReservationSaveResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelValidation.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<RoomReservationSaveResponse> summarize(@RequestBody RoomReservationSave summarizeRequest,
                                                                 @Parameter(description = "Room ID",
                                                                         content = @Content(schema = @Schema(
                                                                                 type = "integer", format = "")))
                                                                 @PathVariable Long id) {
        RoomReservation roomReservation = roomReservationConverter.toRoomReservation(summarizeRequest, id);
        RoomReservation summarizeResponse = roomReservationService.summarize(roomReservation, id);
        RoomReservationSaveResponse roomReservationSaveResponse = roomReservationConverter
                .toRoomReservationSaveResponse(summarizeResponse);
        return ResponseEntity.ok(roomReservationSaveResponse);
    }

    @GetMapping(value = "/{id}/reservations")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List reservations by room ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RoomReservationResponse.class)))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelValidation.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<List<RoomReservationResponse>> findAllRoomReservationById(@Parameter(description = "Room ID",
            content = @Content(schema = @Schema(type = "integer", format = ""))) @PathVariable Long id) {

        List<RoomReservation> roomReservations = roomReservationService.findAllRoomReservationById(id);
        List<RoomReservationResponse> roomReservationResponses = roomReservations.stream()
                .map(roomReservationConverter::toRoomReservationResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomReservationResponses);
    }

    @PostMapping(value = "/{id}/reservations")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create user room reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomReservationSaveResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelValidation.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<RoomReservationSaveResponse> save(@RequestBody @Valid RoomReservationSave roomReservationSave,
                                                            @Parameter(description = "Room ID", content = @Content(
                                                                    schema = @Schema(type = "integer", format = "")))
                                                            @PathVariable Long id) {
        RoomReservation roomReservation = roomReservationConverter.toRoomReservation(roomReservationSave, id);
        RoomReservation savedRoomReservation = roomReservationService.save(roomReservation, id);
        RoomReservationSaveResponse roomReservationSaveResponse = roomReservationConverter
                .toRoomReservationSaveResponse(savedRoomReservation);
        return ResponseEntity.ok(roomReservationSaveResponse);
    }

    @GetMapping(value = "/{id}/reservations/{rid}", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Show a Reservation by ID and room ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomReservationResponse.class))}),
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
    public ResponseEntity<RoomReservationResponse> findAllReservationsById(@Parameter(
            description = "Room ID", content = @Content(schema = @Schema(type = "integer", format = ""))) @PathVariable Long id,
                                                                           @Parameter(description = "Reservation ID", content = @Content(
                                                                                   schema = @Schema(
                                                                                           type = "integer", format = "")))
                                                                           @PathVariable Long rid) {
        RoomReservation foundReservation = roomReservationService.findRoomReservationForRoom(id, rid);
        RoomReservationResponse reservationResponse = roomReservationConverter.toRoomReservationResponse(foundReservation);
        return ResponseEntity.ok(reservationResponse);
    }

    @PutMapping(value = "/{id}/reservations/{rid}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a Reservation by ID and room ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomReservationResponse.class))}),
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
    public ResponseEntity<RoomReservationResponse> updateReservation(@RequestBody RoomReservationUpdate roomRequestUpdate,
                                                                     @Parameter(
                                                                             description = "Room ID", content = @Content(schema = @Schema(type = "integer", format = "")))
                                                                     @PathVariable Long id,
                                                                     @Parameter(description = "Reservation ID", content = @Content(
                                                                             schema = @Schema(
                                                                                     type = "integer", format = "")))
                                                                     @PathVariable Long rid) {
        RoomReservation roomReservation = roomReservationConverter.toRoomReservation(roomRequestUpdate, id);
        RoomReservation updatedReservation = roomReservationService.update(roomReservation, id, rid);
        RoomReservationResponse roomReservationResponse = roomReservationConverter
                .toRoomReservationResponse(updatedReservation);
        return ResponseEntity.ok(roomReservationResponse);
    }

    @DeleteMapping(value = "{id}/reservations/{rid}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Remove a Reservation by ID and room ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(mediaType = "")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<HttpStatus> deleteRoomReservation(@Parameter(description = "Room ID",
            content = @Content(schema = @Schema(type = "integer", format = "")))
                                                            @PathVariable Long id,
                                                            @Parameter(description = "Reservation ID", content = @Content(
                                                                    schema = @Schema(
                                                                            type = "integer", format = "")))
                                                            @PathVariable Long rid) {
        roomReservationService.delete(id, rid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

package com.jbcamp1.moonlighthotel.controller;

import com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferRequest.CarTransferRequest;
import com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferRequest.CarTransferRequestUpdate;
import com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferResponse.CarTransferResponse;
import com.jbcamp1.moonlighthotel.dto.roomreservation.response.RoomReservationResponse;
import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelDuplicate;
import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelValidation;
import com.jbcamp1.moonlighthotel.mapper.CarTransferMapper;
import com.jbcamp1.moonlighthotel.model.CarTransfer;
import com.jbcamp1.moonlighthotel.service.CarTransferService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/cars")
@AllArgsConstructor
@Tag(name = SwaggerConfiguration.TRANSFER_TAG)
public class CarTransferController {

    @Autowired
    private final CarTransferService carTransferService;

    @Autowired
    private final CarTransferMapper carTransferMapper;

    @PostMapping(value = "/{id}/transfers")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create user car transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarTransferResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelValidation.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<CarTransferResponse> save(@RequestBody @Valid CarTransferRequest carTransferRequest,
                                                    @Parameter(description = "Car ID", content = @Content(
                                                            schema = @Schema(type = "integer", format = "")))
                                                    @PathVariable Long id) {
        CarTransfer carTransfer = carTransferMapper.toCarTransfer(carTransferRequest);
        CarTransfer savedCarTransfer = carTransferService.save(carTransfer, id);
        CarTransferResponse carTransferResponse = carTransferMapper.toCarTransferResponse(savedCarTransfer);
        return ResponseEntity.ok(carTransferResponse);
    }

    @GetMapping(value = "/{id}/transfers/{tid}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Show a Transfer by ID and car ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CarTransferResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorModelValidation.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorModelDuplicate.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorModelDuplicate.class))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorModelDuplicate.class)))})
    public ResponseEntity<CarTransferResponse> findById(@Parameter(description = "Car ID", content = @Content(schema = @Schema(type = "integer", format = "")))
                                                        @PathVariable Long id,
                                                        @Parameter(description = "Transfer ID", content = @Content(schema = @Schema(type = "integer", format = "")))
                                                        @PathVariable Long tid) {
        CarTransfer carTransfer = carTransferService.findById(id, tid);
        CarTransferResponse carTransferResponse = carTransferMapper.toCarTransferResponse(carTransfer);

        return ResponseEntity.ok(carTransferResponse);
    }

    @GetMapping(value = "/{id}/transfers")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List transfers by car ID")
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
    public ResponseEntity<List<CarTransferResponse>> findAllTransferByCarId(
            @Parameter(description = "Room ID",
                    content = @Content(schema = @Schema(type = "integer", format = ""))
            ) @PathVariable Long id) {

        List<CarTransfer> carTransfers = carTransferService.findAllRoomReservationByCarId(id);
        List<CarTransferResponse> carTransferResponses = carTransfers.stream()
                .map(carTransferMapper::toCarTransferResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carTransferResponses);
    }


    @PutMapping("/{id}/transfers/{tid}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a Transfer by ID and car ID")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CarTransferResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorModelValidation.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorModelDuplicate.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorModelDuplicate.class))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorModelDuplicate.class)))})
    public ResponseEntity<CarTransferResponse> update(@Parameter(description = "Car ID", content = @Content(schema = @Schema(type = "integer", format = "")))
                                                      @PathVariable Long id,
                                                      @Parameter(description = "Transfer ID", content = @Content(schema = @Schema(type = "integer", format = "")))
                                                      @PathVariable Long tid,
                                                      @RequestBody @Valid CarTransferRequestUpdate carTransferRequestUpdate) {
        CarTransfer carTransfer = carTransferMapper.toCarTransfer(carTransferRequestUpdate);
        CarTransfer updatedTransfer = carTransferService.update(id, tid, carTransfer);
        CarTransferResponse carTransferResponse = carTransferMapper.toCarTransferResponse(updatedTransfer);

        return ResponseEntity.ok(carTransferResponse);
    }

    @PostMapping(value = "/{id}/summarize")
    @Operation(summary = "Calculate transfer info for car ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarTransferResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelValidation.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<CarTransferResponse> summarize(@RequestBody CarTransferRequest summarizeRequest,
                                                         @Parameter(description = "Car ID",
                                                                 content = @Content(schema = @Schema(
                                                                         type = "integer", format = "")))
                                                         @PathVariable Long id) {
        CarTransfer carTransfer = carTransferMapper.toCarTransfer(summarizeRequest);
        CarTransfer summarizedTransfer = carTransferService.summarize(carTransfer, id);
        CarTransferResponse carTransferResponse = carTransferMapper.toCarTransferResponse(summarizedTransfer);
        return ResponseEntity.ok(carTransferResponse);
    }

    @DeleteMapping(value = "/{id}/transfers/{tid}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Remove a Car by ID and transfer TID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content",
                    content = {@Content(mediaType = "")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<HttpStatus> deleteCarTransfer(@Parameter(description = "Car ID", content = @Content(schema = @Schema(type = "integer", format = "")))
                                                        @PathVariable Long id,
                                                        @Parameter(description = "Transfer ID", content = @Content(schema = @Schema(type = "integer", format = "")))
                                                        @PathVariable Long tid) {
        carTransferService.delete(id, tid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
package com.jbcamp1.moonlighthotel.controller;

import com.jbcamp1.moonlighthotel.converter.CarConverter;
import com.jbcamp1.moonlighthotel.dto.car.request.CarRequest;
import com.jbcamp1.moonlighthotel.dto.car.response.CarResponse;
import com.jbcamp1.moonlighthotel.dto.room.response.RoomResponse;
import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelDuplicate;
import com.jbcamp1.moonlighthotel.exception.errorModel.ErrorModelValidation;
import com.jbcamp1.moonlighthotel.filter.CarFilter;
import com.jbcamp1.moonlighthotel.model.Car;
import com.jbcamp1.moonlighthotel.service.CarService;
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
@RequestMapping(value = "/cars")
@AllArgsConstructor
@Tag(name = SwaggerConfiguration.TRANSFER_TAG)
public class CarController {

    @Autowired
    private final CarService carService;

    @Autowired
    private final CarConverter carConverter;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a new car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelValidation.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))})})
    public ResponseEntity<CarResponse> save(@RequestBody @Valid CarRequest carRequestSave) {
        Car car = carConverter.toCar(carRequestSave);
        Car savedCar = carService.save(car);
        CarResponse carResponseSave = carConverter.toCarResponse(savedCar);
        return ResponseEntity.status(HttpStatus.CREATED).body(carResponseSave);
    }

    @GetMapping
    @Operation(summary = "Get available cars")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = CarFilter.class))})
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CarResponse.class)))})
    public ResponseEntity<List<CarResponse>> findAll(@Parameter(hidden = true) CarFilter carFilter) {
        List<Car> cars = carService.findAllAvailableCar(carFilter);
        List<CarResponse> carResponses = cars.stream()
                .map(carConverter::toCarResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carResponses);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Remove a car by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorModelDuplicate.class)))})
    public ResponseEntity<HttpStatus> delete(@Parameter(description = "Car ID",
            content = @Content(schema = @Schema(type = "integer", format = "")))
                                             @PathVariable Long id) {

        carService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a car by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarResponse.class))}),
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
    public ResponseEntity<CarResponse> update(@RequestBody @Valid CarRequest carUpdateRequest,
                                              @Parameter(description = "Car ID",
                                                      content = @Content(schema = @Schema(type = "integer", format = "")))
                                              @PathVariable Long id) {

        Car carForUpdate = carConverter.toCar(carUpdateRequest);
        Car updatedCar = carService.update(carForUpdate, id);
        CarResponse carResponse = carConverter.toCarResponse(updatedCar);
        return ResponseEntity.ok(carResponse);
    }
}

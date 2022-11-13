package com.jbcamp1.moonlighthotel.validator;

import com.jbcamp1.moonlighthotel.exception.InvalidRequestException;
import com.jbcamp1.moonlighthotel.model.Car;
import com.jbcamp1.moonlighthotel.model.CarTransfer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CarTransferValidator {

    @Autowired
    private final ParametersValidator parametersValidator;

    @Autowired
    private final CarValidator carValidator;

    private final String SEATS = "seats";

    private final String MODEL = "model";


    public void validateCarTransfer(CarTransfer carTransfer, Car car) {
        parametersValidator.validateDate(carTransfer.getDate());
        carValidator.validateIsCarAvailable(carTransfer, car);
        validateCarTransferNumberOfSeats(carTransfer, car);
        validateRequestedCarModel(carTransfer, car);
    }

    public void validateCarTransferForUpdate(CarTransfer carTransfer, Car car) {
        parametersValidator.validateDate(carTransfer.getDate());
        carValidator.validateIsCarAvailable(carTransfer, car);
        validateCarTransferNumberOfSeats(carTransfer, car);
    }

    public void validateCarTransferNumberOfSeats(CarTransfer carTransfer, Car car) {
        Integer numberOfRequestedSeats = carTransfer.getSeats();
        Integer numberOfCarSeats = car.getCarCategory().getSeats();
        if (numberOfCarSeats < numberOfRequestedSeats) {
            throw new InvalidRequestException(String
                    .format("Selected car dos not have %d seats", numberOfRequestedSeats), SEATS);
        }
    }

    public void validateRequestedCarModel(CarTransfer carTransfer, Car car) {
        String requestedModel = carTransfer.getModel();
        String carModel = car.getModel();
        if (!requestedModel.equalsIgnoreCase(carModel)) {
            throw new InvalidRequestException(String
                    .format("Selected car dos not have model %s ", requestedModel), MODEL);
        }
    }
}

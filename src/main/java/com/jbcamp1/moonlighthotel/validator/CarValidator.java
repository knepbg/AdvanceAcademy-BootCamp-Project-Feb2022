package com.jbcamp1.moonlighthotel.validator;

import com.jbcamp1.moonlighthotel.exception.DuplicateRecordException;
import com.jbcamp1.moonlighthotel.exception.InvalidRequestException;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.filter.CarFilter;
import com.jbcamp1.moonlighthotel.formatter.DateFormatter;
import com.jbcamp1.moonlighthotel.model.Car;
import com.jbcamp1.moonlighthotel.model.CarTransfer;
import com.jbcamp1.moonlighthotel.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@AllArgsConstructor
@Component
public class CarValidator {

    @Autowired
    private final CarRepository carRepository;

    @Autowired
    private final ParametersValidator parametersValidator;

    @Autowired
    private final DateFormatter dateFormatter;

    public void validateCar(Car car) {

        validateModel(car, car.getId());
    }

    public void validateModel(Car car, Long id) {
        if (carRepository.findByModel(car.getModel()).isPresent()) {
            throw new DuplicateRecordException(
                    String.format("Car model '%s' already exists.", car.getModel()), "model");
        }
    }

    public void verifyCarExists(Long id) {
        carRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("Car with id '%s' does not exist.", id)));
    }

    public void validateRequestQueryParam(CarFilter carFilter) {
        Instant dateForValidation = dateFormatter.stringToInstant(carFilter.getDate());
        parametersValidator.validateDate(dateForValidation);
    }

    public void validateIsCarAvailable(CarTransfer carTransfer, Car car) {
        Long carId = car.getId();
        Instant date = carTransfer.getDate();
        if (!carRepository.IsCarAvailable(carId, date)) {
            throw new InvalidRequestException(String.format("Selected car is not available at %s", date), "date");
        }
    }

}

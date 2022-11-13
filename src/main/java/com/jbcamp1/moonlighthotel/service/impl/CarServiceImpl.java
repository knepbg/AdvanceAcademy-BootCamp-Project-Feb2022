package com.jbcamp1.moonlighthotel.service.impl;

import com.jbcamp1.moonlighthotel.builder.CarBuilder;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.filter.CarFilter;
import com.jbcamp1.moonlighthotel.formatter.DateFormatter;
import com.jbcamp1.moonlighthotel.model.Car;
import com.jbcamp1.moonlighthotel.repository.CarRepository;
import com.jbcamp1.moonlighthotel.service.CarService;
import com.jbcamp1.moonlighthotel.validator.CarValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private final CarRepository carRepository;

    @Autowired
    private final CarValidator carValidator;

    @Autowired
    private final DateFormatter dateFormatter;

    @Autowired
    private final CarBuilder carBuilder;

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Car with id %d does not exist.", id)));
    }

    @Override
    public Car update(Car car, Long id) {
        carValidator.verifyCarExists(id);
        Car builtCar = carBuilder.build(car , id);
        return carRepository.save(builtCar);
    }

    @Override
    public void delete(Long id) {
        Car foundCar = findById(id);
        carRepository.deleteById(foundCar.getId());
    }

    @Override
    public Car save(Car car) {
        carValidator.validateCar(car);
        return carRepository.save(car);
    }

    @Override
    public List<Car> findAllAvailableCar(CarFilter carFilter) {
        if (carFilter.haveParametersBeenRequested()) {
            Integer seats = carFilter.getSeats();
            Instant date = dateFormatter.stringToInstant(carFilter.getDate());
            carValidator.validateRequestQueryParam(carFilter);
            List<Car> allAvailableCar = carRepository.findAllAvailableCar(seats, date);
            return allAvailableCar;
        } else {
            return carRepository.findAll();
        }
    }
}

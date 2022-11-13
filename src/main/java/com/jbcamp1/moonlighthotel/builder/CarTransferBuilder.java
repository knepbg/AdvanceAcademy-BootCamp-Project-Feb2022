package com.jbcamp1.moonlighthotel.builder;

import com.jbcamp1.moonlighthotel.model.Car;
import com.jbcamp1.moonlighthotel.model.CarTransfer;
import com.jbcamp1.moonlighthotel.model.User;
import com.jbcamp1.moonlighthotel.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class CarTransferBuilder {

    @Autowired
    private final UserService userService;

    public CarTransfer buildCarTransfer(CarTransfer carTransfer, Car car) {
        User foundUser = userService.findById(carTransfer.getUser().getId());
        return CarTransfer.builder()
                .created(Instant.now())
                .date(carTransfer.getDate())
                .model(carTransfer.getModel())
                .price(car.getCarCategory().getPrice())
                .seats(carTransfer.getSeats())
                .car(car)
                .user(foundUser)
                .brand(car.getBrand())
                .category(car.getCarCategory().getName())
                .image(car.getImage())
                .build();
    }

    public CarTransfer buildCarTransfer(CarTransfer updatedCarTransfer, Car car, Long id) {
        User foundUser = userService.findById(updatedCarTransfer.getUser().getId());

        return CarTransfer.builder()
                .id(id)
                .car(car)
                .category(car.getCarCategory().getName())
                .brand(car.getBrand())
                .model(car.getModel())
                .image(car.getImage())
                .price(car.getCarCategory().getPrice())
                .date(updatedCarTransfer.getDate())
                .seats(updatedCarTransfer.getSeats())
                .user(foundUser)
                .created(Instant.now())
                .build();
    }

    public CarTransfer buildSummarize(CarTransfer carTransfer, Car car) {
        User foundUser = userService.findById(carTransfer.getUser().getId());
        return CarTransfer.builder()
                .id(car.getId())
                .created(Instant.now())
                .date(carTransfer.getDate())
                .model(carTransfer.getModel())
                .price(car.getCarCategory().getPrice())
                .seats(carTransfer.getSeats())
                .car(car)
                .user(foundUser)
                .brand(car.getBrand())
                .category(car.getCarCategory().getName())
                .image(car.getImage())
                .build();
    }
}

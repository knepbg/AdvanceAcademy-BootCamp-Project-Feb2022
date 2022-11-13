package com.jbcamp1.moonlighthotel.validator;

import com.jbcamp1.moonlighthotel.model.Car;
import com.jbcamp1.moonlighthotel.model.CarCategory;
import com.jbcamp1.moonlighthotel.model.CarTransfer;
import com.jbcamp1.moonlighthotel.model.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ObjectBuilder {

    public Car buildCar() {
        return Car.builder()
                .id(1L)
                .brand("Audi")
                .carCategory(carCategory())
                .image("http://test.jpg")
                .model("RS6")
                .build();
    }

    public CarTransfer buildCarTransfer() {
        return CarTransfer.builder()
                .id(2L)
                .car(buildCar())
                .user(User.builder().build())
                .created(Instant.now())
                .image(buildCar().getImage())
                .category("1")
                .brand(buildCar().getBrand())
                .seats(buildCar().getCarCategory().getSeats())
                .price(buildCar().getCarCategory().getPrice())
                .model(buildCar().getModel())
                .date(Instant.now().plus(1, ChronoUnit.DAYS))
                .build();
    }

    public CarCategory carCategory() {
        return CarCategory.builder()
                .name("sport car")
                .seats(2)
                .price(600D)
                .build();
    }
}

package com.jbcamp1.moonlighthotel.builder;

import com.jbcamp1.moonlighthotel.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarBuilder {

    public Car build(Car car) {
        return Car.builder()
                .id(car.getId())
                .carCategory(car.getCarCategory())
                .brand(car.getBrand())
                .model(car.getModel())
                .image(car.getImage())
                .images(car.getImages())
                .year(car.getYear())
                .build();
    }
    public Car build(Car car, Long id) {
        return Car.builder()
                .id(id)
                .carCategory(car.getCarCategory())
                .brand(car.getBrand())
                .model(car.getModel())
                .image(car.getImage())
                .images(car.getImages())
                .year(car.getYear())
                .build();
    }
}

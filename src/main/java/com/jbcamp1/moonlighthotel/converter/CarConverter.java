package com.jbcamp1.moonlighthotel.converter;

import com.jbcamp1.moonlighthotel.dto.car.request.CarRequest;
import com.jbcamp1.moonlighthotel.dto.car.response.CarResponse;
import com.jbcamp1.moonlighthotel.model.Car;
import com.jbcamp1.moonlighthotel.model.CarCategory;
import com.jbcamp1.moonlighthotel.model.CarImage;
import com.jbcamp1.moonlighthotel.service.CarCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CarConverter {

    @Autowired
    private final CarCategoryService carCategoryService;

    public Car toCar(CarRequest carRequest) {
        CarCategory foundCarCategory = carCategoryService.findById(carRequest.getCategory());

        return Car.builder()
                .carCategory(foundCarCategory)
                .brand(carRequest.getBrand())
                .model(carRequest.getModel())
                .image(carRequest.getImage())
                .images(carRequest.getImages().stream()
                        .map(carImage -> CarImage.builder().carImagePath(carImage).build())
                        .collect(Collectors.toSet()))
                .year(carRequest.getYear())
                .build();
    }

    public CarResponse toCarResponse(Car car) {
        Set<String> carImages = car.getImages().stream()
                .map(CarImage::getCarImagePath)
                .collect(Collectors.toSet());

        return CarResponse.builder()
                .id(car.getId())
                .category(car.getCarCategory().getName())
                .brand(car.getBrand())
                .model(car.getModel())
                .image(car.getImage())
                .images(carImages)
                .seats(car.getCarCategory().getSeats())
                .price(car.getCarCategory().getPrice())
                .build();
    }
}

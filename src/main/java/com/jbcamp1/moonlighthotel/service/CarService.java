package com.jbcamp1.moonlighthotel.service;

import com.jbcamp1.moonlighthotel.filter.CarFilter;
import com.jbcamp1.moonlighthotel.filter.RoomFilter;
import com.jbcamp1.moonlighthotel.model.Car;
import com.jbcamp1.moonlighthotel.model.Room;

import java.util.List;

public interface CarService {

    Car findById(Long id);

    Car update(Car car, Long id);

    void delete(Long id);

    Car save(Car car);

    List<Car> findAllAvailableCar(CarFilter carFilter);
}

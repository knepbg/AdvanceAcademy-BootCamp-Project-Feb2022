package com.jbcamp1.moonlighthotel.repository;

import com.jbcamp1.moonlighthotel.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "SELECT u FROM Car u WHERE u.model =?1")
    Optional<Car> findByModel(String model);

    @Query("SELECT c FROM Car c " +
            "LEFT JOIN CarCategory cc ON cc.id = c.carCategory.id  " +
            "WHERE cc.seats >= ?1 " +
            "AND c.id NOT IN (SELECT ct.car.id FROM CarTransfer ct WHERE ct.date = ?2) " +
            "ORDER BY c.id ASC")
    List<Car> findAllAvailableCar(Integer seats, Instant date);

    @Query("SELECT case when (count(id) > 0)  then false else true end FROM CarTransfer WHERE car_id =?1 AND date=?2")
    boolean IsCarAvailable(Long id, Instant date);

}

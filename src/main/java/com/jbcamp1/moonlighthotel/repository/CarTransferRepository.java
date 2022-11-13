package com.jbcamp1.moonlighthotel.repository;

import com.jbcamp1.moonlighthotel.model.CarTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarTransferRepository extends JpaRepository<CarTransfer, Long> {

    @Query(value = "SELECT ct FROM CarTransfer ct WHERE ct.car.id = ?1 AND ct.id = ?2")
    Optional<CarTransfer> findById(Long id, Long tid);

    List<CarTransfer> findAllRoomReservationByCarId(Long id);
}

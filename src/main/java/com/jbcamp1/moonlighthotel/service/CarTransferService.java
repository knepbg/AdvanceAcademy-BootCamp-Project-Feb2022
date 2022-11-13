package com.jbcamp1.moonlighthotel.service;

import com.jbcamp1.moonlighthotel.model.CarTransfer;

import java.util.List;

public interface CarTransferService {

    CarTransfer findById(Long id, Long tid);

    CarTransfer update(Long id, Long tid, CarTransfer carTransfer);

    void delete(Long id);

    List<CarTransfer> findAllRoomReservationByCarId(Long id);

    List<CarTransfer> findAll();

    CarTransfer save(CarTransfer carTransfer, Long id);

    CarTransfer summarize(CarTransfer transfer, Long cid);

    void delete(Long id, Long tid);

}

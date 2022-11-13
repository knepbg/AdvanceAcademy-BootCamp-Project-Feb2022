package com.jbcamp1.moonlighthotel.service.impl;

import com.jbcamp1.moonlighthotel.builder.CarTransferBuilder;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.model.Car;
import com.jbcamp1.moonlighthotel.model.CarTransfer;
import com.jbcamp1.moonlighthotel.repository.CarTransferRepository;
import com.jbcamp1.moonlighthotel.service.CarService;
import com.jbcamp1.moonlighthotel.service.CarTransferService;
import com.jbcamp1.moonlighthotel.validator.CarTransferValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarTransferServiceImpl implements CarTransferService {

    @Autowired
    private final CarTransferRepository carTransferRepository;

    @Autowired
    private final CarTransferValidator carTransferValidator;

    @Autowired
    private final CarTransferBuilder carTransferBuilder;

    @Autowired
    private final CarService carService;

    @Override
    public CarTransfer findById(Long id, Long tid) {
        return carTransferRepository.findById(id, tid)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("Transfer with id '%d' for car with id '%d' was not found.", tid, id)));
    }

    @Override
    public CarTransfer update(Long id, Long tid, CarTransfer carTransfer) {
        CarTransfer foundCarTransfer = findById(id, tid);

        carTransferValidator.validateCarTransferForUpdate(carTransfer, foundCarTransfer.getCar());
        CarTransfer builtCarTransfer = carTransferBuilder.buildCarTransfer(carTransfer, foundCarTransfer.getCar(), foundCarTransfer.getId());
        return carTransferRepository.save(builtCarTransfer);
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public List<CarTransfer> findAllRoomReservationByCarId(Long id) {
        return carTransferRepository.findAllRoomReservationByCarId(id);
    }

    @Override
    public List<CarTransfer> findAll() {
        return null;
    }

    @Override
    public CarTransfer save(CarTransfer carTransfer, Long id) {
        Car foundCar = carService.findById(id);
        carTransferValidator.validateCarTransfer(carTransfer, foundCar);
        CarTransfer carTransferForSave = carTransferBuilder.buildCarTransfer(carTransfer, foundCar);
        return carTransferRepository.save(carTransferForSave);
    }

    @Override
    public CarTransfer summarize(CarTransfer transfer, Long cid) {
        Car foundCar = carService.findById(cid);
        carTransferValidator.validateCarTransfer(transfer, foundCar);
        CarTransfer carTransferForSummarize = carTransferBuilder.buildSummarize(transfer, foundCar);
        return carTransferForSummarize;
    }

    @Override
    public void delete(Long id, Long tid) {
        CarTransfer foundCarTransfer = findById(id, tid);
        carTransferRepository.deleteById(foundCarTransfer.getId());
    }
}

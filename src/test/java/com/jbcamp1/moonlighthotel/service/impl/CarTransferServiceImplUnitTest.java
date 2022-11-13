package com.jbcamp1.moonlighthotel.service.impl;

import com.jbcamp1.moonlighthotel.builder.CarTransferBuilder;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.model.Car;
import com.jbcamp1.moonlighthotel.model.CarTransfer;
import com.jbcamp1.moonlighthotel.repository.CarTransferRepository;
import com.jbcamp1.moonlighthotel.service.CarService;
import com.jbcamp1.moonlighthotel.validator.CarTransferValidator;
import com.jbcamp1.moonlighthotel.validator.ObjectBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarTransferServiceImplUnitTest {

    @Mock
    private CarTransferRepository carTransferRepository;

    @Mock
    private CarTransferValidator carTransferValidator;

    @Mock
    private CarTransferBuilder carTransferBuilder;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarTransferServiceImpl carTransferService;

    private final ObjectBuilder objectBuilder = new ObjectBuilder();

    private Throwable thrown;

    @Test
    public void verifyCarTransferSave() {
        CarTransfer carTransfer = objectBuilder.buildCarTransfer();
        Car car = objectBuilder.buildCar();
        Long id = car.getId();

        when(carService.findById(any())).thenReturn(car);
        when(carTransferBuilder.buildCarTransfer(any(), any())).thenReturn(carTransfer);

        carTransferService.save(carTransfer, id);

        verify(carService, times(1)).findById(id);
        verify(carTransferValidator, times(1)).validateCarTransfer(carTransfer, car);
        verify(carTransferBuilder, times(1)).buildCarTransfer(carTransfer, car);
        verify(carTransferRepository, times(1)).save(carTransfer);
    }

    @Test
    public void validateThatIdOfNonExistentCarTransferRecordShouldThrowRecordNotFoundException() {
        Long id = 1L;
        Long tid = 6000L;

        when(carTransferRepository.findById(any(), any()))
                .thenReturn(Optional.empty());

        thrown = catchThrowable(() -> carTransferService.findById(id, tid));

        assertThat(thrown)
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage("Transfer with id '%d' for car with id '%d' was not found.", tid, id);
    }

    @Test
    public void verifyFindById() {
        Long id = 1L;
        Long tid = 1L;

        when(carTransferRepository.findById(any(), any()))
                .thenReturn(Optional.of(CarTransfer.builder().id(id).build()));

        carTransferService.findById(id, tid);

        verify(carTransferRepository, times(1)).findById(id, tid);
    }
}

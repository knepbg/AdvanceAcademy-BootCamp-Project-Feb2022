package com.jbcamp1.moonlighthotel.service.impl;

import com.jbcamp1.moonlighthotel.builder.CarBuilder;
import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.model.Car;
import com.jbcamp1.moonlighthotel.repository.CarRepository;
import com.jbcamp1.moonlighthotel.validator.CarValidator;
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
public class CarServiceImplTest {

    private Throwable thrown;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarBuilder carBuilder;

    @Mock
    private CarValidator carValidator;

    @InjectMocks
    private CarServiceImpl carServiceImpl;

    @Test
    public void validateThatIdOfNonExistentCarRecordShouldThrowRecordNotFoundException() {
        Long id = 1L;

        when(carRepository.findById(any()))
                .thenReturn(Optional.empty());

        thrown = catchThrowable(() -> carServiceImpl.findById(id));

        assertThat(thrown)
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage("Car with id '%s' does not exist.", id);
    }

    @Test
    public void testFindById() {
        Long id = 1L;

        when(carRepository.findById(any()))
                .thenReturn(Optional.of(Car.builder().build()));

        carServiceImpl.findById(id);

        verify(carRepository, times(1)).findById(id);
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;

        when(carRepository.findById(any()))
                .thenReturn(Optional.of(Car.builder().id(id).build()));

        carServiceImpl.delete(id);

        verify(carRepository, times(1)).deleteById(id);
    }
}

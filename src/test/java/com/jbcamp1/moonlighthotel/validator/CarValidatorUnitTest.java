package com.jbcamp1.moonlighthotel.validator;

import com.jbcamp1.moonlighthotel.exception.InvalidRequestException;
import com.jbcamp1.moonlighthotel.model.Car;
import com.jbcamp1.moonlighthotel.model.CarTransfer;
import com.jbcamp1.moonlighthotel.repository.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarValidatorUnitTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarValidator carValidator;

    private Throwable thrown;

    private final ObjectBuilder objectBuilder = new ObjectBuilder();


    @Test
    public void ifCarIsNotAvailableShouldTrowException() {
        Car car = objectBuilder.buildCar();
        CarTransfer carTransfer = objectBuilder.buildCarTransfer();
        when(carRepository.IsCarAvailable(any(), any()))
                .thenReturn(false);

        thrown = catchThrowable(() -> carValidator.validateIsCarAvailable(carTransfer, car));
        assertThat(thrown).isInstanceOf(InvalidRequestException.class)
                .hasMessage(String.format("Selected car is not available at %s", carTransfer.getDate()));
    }
}

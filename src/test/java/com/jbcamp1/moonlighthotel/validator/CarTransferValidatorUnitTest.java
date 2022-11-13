package com.jbcamp1.moonlighthotel.validator;

import com.jbcamp1.moonlighthotel.exception.InvalidRequestException;
import com.jbcamp1.moonlighthotel.model.Car;
import com.jbcamp1.moonlighthotel.model.CarTransfer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CarTransferValidatorUnitTest {

    @Mock
    private ParametersValidator parametersValidator;

    @Mock
    private CarValidator carValidator;

    @InjectMocks
    private CarTransferValidator carTransferValidator;

    private Throwable thrown;

    private final ObjectBuilder objectBuilder = new ObjectBuilder();

    @Test
    public void validateCarTransfer() {
        Car car = objectBuilder.buildCar();
        CarTransfer carTransfer = objectBuilder.buildCarTransfer();

        carTransferValidator.validateCarTransfer(carTransfer, car);

        verify(parametersValidator, times(1)).validateDate(carTransfer.getDate());
        verify(carValidator, times(1)).validateIsCarAvailable(carTransfer, car);
    }

    @Test
    public void ifRequestedTransferNumberOfSeatsIsMoreThenCarSeatsShouldThrowException() {
        Car car = objectBuilder.buildCar();
        CarTransfer carTransfer = CarTransfer.builder()
                .seats(10)
                .build();

        thrown = catchThrowable(() -> carTransferValidator.validateCarTransferNumberOfSeats(carTransfer, car));
        assertThat(thrown).isInstanceOf(InvalidRequestException.class)
                .hasMessage(String.format("Selected car dos not have %d seats", carTransfer.getSeats()));
    }

    @Test
    public void ifRequestedTransferModelIsNotSelectedCarModelShouldTrowException() {
        Car car = Car.builder()
                .model("test")
                .build();
        CarTransfer carTransfer = objectBuilder.buildCarTransfer();

        thrown = catchThrowable(() -> carTransferValidator.validateRequestedCarModel(carTransfer, car));
        assertThat(thrown).isInstanceOf(InvalidRequestException.class)
                .hasMessage(String.format("Selected car dos not have model %s ", carTransfer.getModel()));
    }
}

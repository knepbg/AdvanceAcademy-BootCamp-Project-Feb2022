package api.car.creator;

import api.ObjectCreator;
import com.jbcamp1.moonlighthotel.dto.car.request.CarRequest;

public class CarCreator {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public CarRequest createCar() {
        String content = "classpath:carRequest.json";
        return objectCreator.createObject(content, CarRequest.class);
    }
}

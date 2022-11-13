package api.transfer.creator;

import api.ObjectCreator;
import com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferRequest.CarTransferRequest;

public class CarTransferCreator {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public CarTransferRequest createCarTransfer() {
        String content = "classpath:carTransferSave.json";
        return objectCreator.createObject(content, CarTransferRequest.class);
    }

    public CarTransferRequest createCarTransferBadRequest() {
        String content = "classpath:carTransferSaveBadRequest.json";
        return objectCreator.createObject(content, CarTransferRequest.class);
    }
}

package api.transfer.updater;

import api.ObjectCreator;
import com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferRequest.CarTransferRequestUpdate;

public class CarTransferUpdater {

    private final ObjectCreator objectCreator = new ObjectCreator();

    public CarTransferRequestUpdate updateCarTransfer() {
        String content = "classpath:carTransferUpdate.json";

        return objectCreator.createObject(content, CarTransferRequestUpdate.class);
    }

    public CarTransferRequestUpdate updateCarTransferBadRequest() {
        String content = "classpath:carTransferUpdateBadRequest.json";

        return objectCreator.createObject(content, CarTransferRequestUpdate.class);
    }
}

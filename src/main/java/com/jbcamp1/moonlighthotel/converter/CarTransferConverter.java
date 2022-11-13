package com.jbcamp1.moonlighthotel.converter;

import com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferResponse.CarTransferResponse;
import com.jbcamp1.moonlighthotel.formatter.DateFormatter;
import com.jbcamp1.moonlighthotel.model.CarTransfer;
import com.jbcamp1.moonlighthotel.service.CarTransferService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CarTransferConverter {

    @Autowired
    private final DateFormatter dateFormatter;


    public CarTransferResponse toCarTransferResponse(CarTransfer carTransfer) {
        return CarTransferResponse.builder()
                .id(carTransfer.getId())
                .category(carTransfer.getCategory())
                .brand(carTransfer.getBrand())
                .model(carTransfer.getModel())
                .image(carTransfer.getImage())
                .price(carTransfer.getPrice())
                .date(dateFormatter.instantToString(carTransfer.getDate()))
                .userId(carTransfer.getUser().getId())
                .user(carTransfer.getUser().getFullName())
                .created(dateFormatter.instantToString(carTransfer.getCreated()))
                .build();
    }
}

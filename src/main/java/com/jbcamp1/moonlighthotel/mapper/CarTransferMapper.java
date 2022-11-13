package com.jbcamp1.moonlighthotel.mapper;

import com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferRequest.CarTransferRequest;
import com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferRequest.CarTransferRequestUpdate;
import com.jbcamp1.moonlighthotel.dto.carTransfer.carTransferResponse.CarTransferResponse;
import com.jbcamp1.moonlighthotel.model.CarTransfer;
import com.jbcamp1.moonlighthotel.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarTransferMapper {

    @Mapping(target = "id", source = "user")
    User map(Long user);

    @Mapping(target = "id", ignore = true)
    CarTransfer toCarTransfer(CarTransferRequest carTransferRequest);

    @Mapping(target = "id", ignore = true)
    CarTransfer toCarTransfer(CarTransferRequestUpdate carTransferRequestUpdate);

    @Mapping(target = "user", expression = "java(carTransfer.getUser().getFullName())")
    @Mapping(target = "userId", expression = "java(carTransfer.getUser().getId())")
    CarTransferResponse toCarTransferResponse(CarTransfer carTransfer);


}

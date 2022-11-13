package com.jbcamp1.moonlighthotel.service.impl;

import com.jbcamp1.moonlighthotel.exception.RecordNotFoundException;
import com.jbcamp1.moonlighthotel.model.CarCategory;
import com.jbcamp1.moonlighthotel.repository.CarCategoryRepository;
import com.jbcamp1.moonlighthotel.service.CarCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CarCategoryServiceImpl implements CarCategoryService {

    @Autowired
    private final CarCategoryRepository carCategoryRepository;

    @Override
    public CarCategory findById(Long id) {
        return carCategoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("Category with id '%d' was not found.", id)));
    }
}

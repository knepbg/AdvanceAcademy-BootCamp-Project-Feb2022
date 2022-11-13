package com.jbcamp1.moonlighthotel.repository;

import com.jbcamp1.moonlighthotel.model.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarCategoryRepository extends JpaRepository<CarCategory, Long> {
}

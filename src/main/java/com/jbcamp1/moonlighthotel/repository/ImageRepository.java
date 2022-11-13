package com.jbcamp1.moonlighthotel.repository;

import com.jbcamp1.moonlighthotel.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByImagePath(String imagePath);
}

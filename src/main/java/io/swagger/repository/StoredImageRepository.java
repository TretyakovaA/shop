package io.swagger.repository;

import io.swagger.model.StoredImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoredImageRepository extends JpaRepository<StoredImage, Integer> {
}

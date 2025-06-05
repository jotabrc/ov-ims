package io.github.jotabrc.ov_ims_product.repository;

import io.github.jotabrc.ov_ims_product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByUuid(String uuid);
    Optional<Category> findByName(String uuid);
}

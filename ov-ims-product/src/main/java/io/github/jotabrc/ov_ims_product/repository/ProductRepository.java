package io.github.jotabrc.ov_ims_product.repository;

import io.github.jotabrc.ov_ims_product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByUuid(final String uuid);

    @Query("SELECT p FROM tb_product p " +
            "JOIN p.categories c " +
            "WHERE p.isActive = true " +
            "AND c.isActive = true " +
            "AND (:uuid IS NULL OR p.uuid = :uuid) " +
            "OR (:cName IS NULL OR c.name = :cName)")
    Page<Product> getWith(@Param("uuid") String uuid, @Param("cName") String cName, Pageable pageable);
}

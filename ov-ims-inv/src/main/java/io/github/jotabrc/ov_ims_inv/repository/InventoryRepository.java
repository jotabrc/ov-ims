package io.github.jotabrc.ov_ims_inv.repository;

import io.github.jotabrc.ov_ims_inv.model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductUuid(String productUuid);
    Page<Inventory> findByInventoryBetween(int minValue, int maxValue, Pageable pageable);
    Page<Inventory> findByInventoryGreaterThan(int minValue, Pageable pageable);
    Page<Inventory> findByInventoryLessThan(int maxValue, Pageable pageable);
    boolean existsByProductUuid(String productUuid);
}

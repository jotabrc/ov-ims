package io.github.jotabrc.ov_ims_order.repository;

import io.github.jotabrc.ov_ims_order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByUuid(String uuid);
    Page<Order> findByUuid(String uuid, Pageable pageable);
    Page<Order> findByPlacedBy(String placedBy, Pageable pageable);
}

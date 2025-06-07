package io.github.jotabrc.ov_ims_order.repository;

import io.github.jotabrc.ov_ims_order.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<Detail, Long> {
}

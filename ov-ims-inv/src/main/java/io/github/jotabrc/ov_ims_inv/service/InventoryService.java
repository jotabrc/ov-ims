package io.github.jotabrc.ov_ims_inv.service;

import io.github.jotabrc.ov_ims_inv.dto.PageFilter;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

    void save(String productUuid);
    Object get(PageFilter pageFilter, Pageable pageable);
}

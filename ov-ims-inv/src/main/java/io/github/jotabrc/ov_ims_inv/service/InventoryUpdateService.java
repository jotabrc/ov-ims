package io.github.jotabrc.ov_ims_inv.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_ims_inv.dto.InventoryDtoUpdate;
import io.github.jotabrc.ov_ims_inv.dto.OrderDtoKafka;

public interface InventoryUpdateService {

    void update(InventoryDtoUpdate dto);
    void update(OrderDtoKafka dto) throws JsonProcessingException;
}

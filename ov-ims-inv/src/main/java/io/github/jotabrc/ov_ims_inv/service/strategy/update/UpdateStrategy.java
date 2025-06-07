package io.github.jotabrc.ov_ims_inv.service.strategy.update;

import io.github.jotabrc.ov_ims_inv.dto.InventoryDtoUpdate;
import io.github.jotabrc.ov_ims_inv.model.Inventory;

public interface UpdateStrategy {

    void update(Inventory inventory, InventoryDtoUpdate dto);
}

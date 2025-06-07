package io.github.jotabrc.ov_ims_inv.service.strategy.update;

import io.github.jotabrc.ov_ims_inv.dto.InventoryDtoUpdate;
import io.github.jotabrc.ov_ims_inv.model.Inventory;
import org.springframework.stereotype.Component;

@Component
public class UpdateStrategyInventoryAdd implements UpdateStrategy {

    @Override
    public void update(Inventory inventory, InventoryDtoUpdate dto) {
        inventory.setInventory(
                inventory.getInventory() + dto.getData().getInventory()
        );
    }
}

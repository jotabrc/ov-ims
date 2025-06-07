package io.github.jotabrc.ov_ims_inv.service.strategy.update;

import io.github.jotabrc.ov_ims_inv.controller.handler.UnavailableStockException;
import io.github.jotabrc.ov_ims_inv.dto.InventoryDtoUpdate;
import io.github.jotabrc.ov_ims_inv.model.Inventory;
import org.springframework.stereotype.Component;

@Component
public class UpdateStrategyReserveRemove implements UpdateStrategy {

    @Override
    public void update(final Inventory inventory, final InventoryDtoUpdate dto) {
        removeReserveOrThrow(inventory, dto);
    }

    public void removeReserveOrThrow(final Inventory inventory, final InventoryDtoUpdate dto) {
        if (inventory.getReservedInventory() >= dto.getData().getInventory())
            removeReservedQuantity(inventory, dto);
        else
            throw  new UnavailableStockException(
                    "Reserved amount (%d) is less than requested amount (%s)"
                            .formatted(inventory.getReservedInventory(), dto.getData().getInventory())
            );
    }

    private void removeReservedQuantity(final Inventory inventory, final InventoryDtoUpdate dto) {
        inventory.setReservedInventory(
                inventory.getReservedInventory() - dto.getData().getInventory()
        );
    }
}

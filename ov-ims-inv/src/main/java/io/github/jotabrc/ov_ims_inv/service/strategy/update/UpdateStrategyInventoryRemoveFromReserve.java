package io.github.jotabrc.ov_ims_inv.service.strategy.update;

import io.github.jotabrc.ov_ims_inv.controller.handler.UnavailableStockException;
import io.github.jotabrc.ov_ims_inv.dto.InventoryDtoUpdate;
import io.github.jotabrc.ov_ims_inv.model.Inventory;
import org.springframework.stereotype.Component;

@Component
public class UpdateStrategyInventoryRemoveFromReserve implements UpdateStrategy {

    @Override
    public void update(final Inventory inventory, final InventoryDtoUpdate dto) {
        reservedMatchRequestOrThrow(
                inventory.getReservedInventory(),
                dto.getData().getInventory(),
                inventory.getProductUuid()
        );
        updateInventory(inventory, dto);
    }

    private void reservedMatchRequestOrThrow(final int reserved, final int request, final String productUuid) {
        if (request > reserved)
            throw new UnavailableStockException(
                    "Product %s: reserved quantity (%d) is less than requested amount (%d)"
                            .formatted(productUuid, reserved, request)
            );
    }

    private void updateInventory(final Inventory inventory, final InventoryDtoUpdate dto) {
        inventory
                .setReservedInventory(
                        inventory.getReservedInventory() - dto.getData().getInventory()
                )
                .setInventory(
                        inventory.getInventory() - dto.getData().getInventory()
                );
    }
}

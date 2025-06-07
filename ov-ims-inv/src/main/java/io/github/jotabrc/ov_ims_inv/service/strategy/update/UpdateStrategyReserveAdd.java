package io.github.jotabrc.ov_ims_inv.service.strategy.update;

import io.github.jotabrc.ov_ims_inv.controller.handler.UnavailableStockException;
import io.github.jotabrc.ov_ims_inv.dto.InventoryDtoUpdate;
import io.github.jotabrc.ov_ims_inv.model.Inventory;
import org.springframework.stereotype.Component;

@Component
public class UpdateStrategyReserveAdd implements UpdateStrategy {

    @Override
    public void update(final Inventory inventory, final InventoryDtoUpdate dto) {
        canReserveOrThrow(inventory, dto);
    }

    private void canReserveOrThrow(final Inventory inventory, final InventoryDtoUpdate dto) {
        final int availableStock = getInventoryAvailability(
                inventory.getInventory(),
                inventory.getReservedInventory(),
                dto.getData().getInventory()
        );

        if (availableStock >= 0)
            inventory.setReservedInventory(
                    getNewReserveQuantity(
                            inventory.getReservedInventory(),
                            dto.getData().getInventory()
                    )
            );
        else
            throw new UnavailableStockException(
                    "Product %s: Available stock (%d) is insufficient for requested amount (%d)"
                            .formatted(inventory.getProductUuid(), getAvailableStock(
                                    inventory.getInventory(), inventory.getReservedInventory()
                            ), dto.getData().getInventory()));

    }

    private int getInventoryAvailability(
            final int inventory,
            final int reserved,
            final int requested
    ) {
        return inventory - reserved - requested;
    }

    private int getAvailableStock(
            final int inventory,
            final int reserved
    ) {
        return inventory - reserved;
    }

    private int getNewReserveQuantity(final int reserved, final int requested) {
        return reserved + requested;
    }
}

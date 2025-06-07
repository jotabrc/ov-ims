package io.github.jotabrc.ov_ims_inv.util;

import io.github.jotabrc.ov_ims_inv.dto.InventoryDto;
import io.github.jotabrc.ov_ims_inv.model.Inventory;
import org.springframework.stereotype.Component;

@Component
public class DtoMapperImpl implements DtoMapper {

    @Override
    public InventoryDto toDto(Inventory inventory) {
        return new InventoryDto(
                inventory.getProductUuid(),
                inventory.getInventory(),
                inventory.getReservedInventory()
        );
    }
}

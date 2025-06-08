package io.github.jotabrc.ov_ims_inv.util;

import io.github.jotabrc.ov_ims_inv.dto.*;
import io.github.jotabrc.ov_ims_inv.model.Inventory;
import org.springframework.stereotype.Component;

@Component
public class DtoMapperImpl implements DtoMapper {

    @Override
    public InventoryDto toDto(final Inventory inventory) {
        return new InventoryDto(
                inventory.getProductUuid(),
                inventory.getInventory(),
                inventory.getReservedInventory()
        );
    }

    @Override
    public InventoryDtoUpdate toDto(final ProductDtoUuidAndQuantity dto, final UpdateType type) {
        return new InventoryDtoUpdate(
                type,
                toDto(dto)
        );
    }

    @Override
    public InventoryDtoUpdateData toDto(ProductDtoUuidAndQuantity dto) {
        return new InventoryDtoUpdateData(
                dto.getUuid(),
                dto.getQuantity()
        );
    }
}

package io.github.jotabrc.ov_ims_inv.util;

import io.github.jotabrc.ov_ims_inv.dto.*;
import io.github.jotabrc.ov_ims_inv.model.Inventory;

public interface DtoMapper {

    InventoryDto toDto(Inventory inventory);
    InventoryDtoUpdate toDto(ProductDtoUuidAndQuantity dto, UpdateType type);
    InventoryDtoUpdateData toDto(ProductDtoUuidAndQuantity dto);
}

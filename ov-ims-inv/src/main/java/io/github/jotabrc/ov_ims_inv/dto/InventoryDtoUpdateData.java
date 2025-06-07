package io.github.jotabrc.ov_ims_inv.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class InventoryDtoUpdateData extends InventoryDtoAbs {

    public InventoryDtoUpdateData(@NotNull String productUuid, @Min(0) int inventory) {
        super(productUuid, inventory);
    }
}

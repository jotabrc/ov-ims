package io.github.jotabrc.ov_ims_inv.dto;

import io.github.jotabrc.ov_ims_inv.validation.StringType;
import io.github.jotabrc.ov_ims_inv.validation.ValidString;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public abstract class InventoryDtoAbs {

    @ValidString(error = "Invalid Product UUID format", type = StringType.UUID)
    private final String productUuid;

    @Min(0)
    private final int inventory;

    protected InventoryDtoAbs(String productUuid, int inventory) {
        this.productUuid = productUuid;
        this.inventory = inventory;
    }
}

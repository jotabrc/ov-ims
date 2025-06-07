package io.github.jotabrc.ov_ims_inv.dto;

import lombok.Getter;

@Getter
public abstract class InventoryDtoAbs {

    private final String productUuid;
    private final int inventory;

    protected InventoryDtoAbs(String productUuid, int inventory) {
        this.productUuid = productUuid;
        this.inventory = inventory;
    }
}

package io.github.jotabrc.ov_ims_inv.dto;

import lombok.Getter;

@Getter
public class InventoryDtoUpdateData extends InventoryDtoAbs {

    public InventoryDtoUpdateData(String productUuid, int inventory) {
        super(productUuid, inventory);
    }
}

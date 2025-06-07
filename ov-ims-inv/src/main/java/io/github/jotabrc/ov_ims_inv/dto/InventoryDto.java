package io.github.jotabrc.ov_ims_inv.dto;

import lombok.Getter;

@Getter
public class InventoryDto extends InventoryDtoAbs {

    private final int reservedInventory;

    public InventoryDto(String productUuid, int inventory, int reservedInventory) {
        super(productUuid, inventory);
        this.reservedInventory = reservedInventory;
    }
}

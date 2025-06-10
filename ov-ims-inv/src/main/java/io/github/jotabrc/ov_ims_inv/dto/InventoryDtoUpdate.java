package io.github.jotabrc.ov_ims_inv.dto;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class InventoryDtoUpdate {

    private final UpdateType type;

    @Valid
    private final InventoryDtoUpdateData data;

    public InventoryDtoUpdate(UpdateType type, InventoryDtoUpdateData data) {
        this.type = type;
        this.data = data;
    }
}

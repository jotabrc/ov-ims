package io.github.jotabrc.ov_ims_inv.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UpdateType {

    RESERVE_ADD,
    RESERVE_REMOVE,
    INVENTORY_REMOVE_FROM_RESERVE,
    INVENTORY_ADD;

    @JsonCreator
    public static UpdateType fromString(String value) {
        return value == null ? null : UpdateType.valueOf(value.toUpperCase());
    }
}

package io.github.jotabrc.ov_ims_order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UpdateType {

    CANCEL,
    RETURN,
    NEW_STATUS;

    @JsonCreator
    public static UpdateType fromString(String value) {
        return value == null ? null : UpdateType.valueOf(value.toUpperCase());
    }
}

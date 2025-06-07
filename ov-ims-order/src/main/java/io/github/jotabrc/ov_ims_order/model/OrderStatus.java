package io.github.jotabrc.ov_ims_order.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderStatus {

    PLACED,
    HAS_INVENTORY,
    PROCESSING,
    CANCELLED,
    IN_DELIVERY,
    DELIVERED;

    @JsonCreator
    public static OrderStatus fromString(String value) {
        return value == null ? null : OrderStatus.valueOf(value.toUpperCase());
    }
}

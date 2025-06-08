package io.github.jotabrc.ov_ims_order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ProductDtoUuidAndQuantity {

    private final String uuid;
    private final int quantity;

    @JsonCreator
    public ProductDtoUuidAndQuantity(
            @JsonProperty("uuid") String uuid,
            @JsonProperty("quantity") int quantity
    ) {
        this.uuid = uuid;
        this.quantity = quantity;
    }
}

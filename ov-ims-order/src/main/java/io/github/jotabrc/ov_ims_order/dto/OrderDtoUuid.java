package io.github.jotabrc.ov_ims_order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OrderDtoUuid {

    private final String uuid;

    @JsonCreator
    public OrderDtoUuid(
            @JsonProperty("uuid") String uuid
    ) {
        this.uuid = uuid;
    }
}

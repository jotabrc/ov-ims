package io.github.jotabrc.ov_ims_order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderDtoKafka {

    private final String uuid;
    private final String type;
    private final List<ProductDtoUuidAndQuantity> details;

    @JsonCreator
    public OrderDtoKafka(
            @JsonProperty("uuid") String uuid,
            @JsonProperty("type") String type,
            @JsonProperty("details") List<ProductDtoUuidAndQuantity> details
    ) {
        this.uuid = uuid;
        this.type = type;
        this.details = details;
    }
}

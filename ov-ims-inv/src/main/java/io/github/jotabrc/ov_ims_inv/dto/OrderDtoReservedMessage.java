package io.github.jotabrc.ov_ims_inv.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OrderDtoReservedMessage {

    private final String uuid;
    private final boolean reserved;

    @JsonCreator
    public OrderDtoReservedMessage(
            @JsonProperty("uuid") String uuid,
            @JsonProperty("reserved") boolean reserved) {
        this.uuid = uuid;
        this.reserved = reserved;
    }
}

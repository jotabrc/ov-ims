package io.github.jotabrc.ov_ims_inv.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ProductDtoUuidOnly {

    private final String uuid;

    @JsonCreator
    public ProductDtoUuidOnly(
            @JsonProperty("uuid") String uuid
    ) {
        this.uuid = uuid;
    }
}

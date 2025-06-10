package io.github.jotabrc.ov_ims_product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ProductDtoUuidOnly implements Serializable {

    private final String uuid;

    @JsonCreator
    public ProductDtoUuidOnly(
            @JsonProperty("uuid") String uuid
    ) {
        this.uuid = uuid;
    }
}

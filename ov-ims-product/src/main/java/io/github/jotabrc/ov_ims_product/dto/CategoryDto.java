package io.github.jotabrc.ov_ims_product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CategoryDto implements Serializable {

    private String uuid;
    @NotNull
    private final String name;

    @JsonCreator
    public CategoryDto(
            @JsonProperty("uuid") String uuid,
            @JsonProperty("name") String name
    ) {
        this.uuid = uuid;
        this.name = name;
    }
}

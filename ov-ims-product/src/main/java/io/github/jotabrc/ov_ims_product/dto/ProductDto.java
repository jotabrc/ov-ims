package io.github.jotabrc.ov_ims_product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Builder
public class ProductDto implements Serializable {

    private String uuid;

    @NotNull
    private final String name;
    private final String description;

    @Valid
    private final Set<CategoryDto> categories;

    @JsonCreator
    public ProductDto(
            @JsonProperty("uuid") String uuid,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("categories") Set<CategoryDto> categories
    ) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.categories = categories;
    }
}

package io.github.jotabrc.ov_ims_product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.jotabrc.ov_ims_product.validation.StringType;
import io.github.jotabrc.ov_ims_product.validation.ValidString;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Builder
public class ProductDtoAdd implements Serializable {

    @NotNull @ValidString(error = "Invalid Product name format", type = StringType.NAME)
    private final String name;

    @ValidString(error = "Invalid Product description format", type = StringType.STRING, isRequired = false)
    private final String description;

    @Valid
    private final Set<CategoryDtoAdd> categories;

    @JsonCreator
    public ProductDtoAdd(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("categories") Set<CategoryDtoAdd> categories
    ) {
        this.name = name;
        this.description = description;
        this.categories = categories;
    }
}

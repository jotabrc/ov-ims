package io.github.jotabrc.ov_ims_product.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter @Builder
public class ProductDto {

    private String uuid;

    @NotNull
    private final String name;
    private final String description;

    @Valid
    private final Set<CategoryDto> categories;
}

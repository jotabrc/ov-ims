package io.github.jotabrc.ov_ims_product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CategoryDto {

    private String uuid;
    @NotNull
    private final String name;
}

package io.github.jotabrc.ov_ims_product.dto;

import io.github.jotabrc.ov_ims_product.validation.StringType;
import io.github.jotabrc.ov_ims_product.validation.ValidString;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PageFilter {

    @ValidString(error = "Invalid UUID format", type = StringType.UUID, isRequired = false)
    private final String uuid;

    @ValidString(error = "Invalid Category name format", type = StringType.NAME, isRequired = false)
    private final String category;
}

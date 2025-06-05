package io.github.jotabrc.ov_ims_product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PageFilter {

    private final String uuid;
    private final String category;
}

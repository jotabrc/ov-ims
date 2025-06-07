package io.github.jotabrc.ov_ims_inv.dto;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PageFilter {

    private final String productUuid;
    private final Integer minValue;
    private final Integer maxValue;
}

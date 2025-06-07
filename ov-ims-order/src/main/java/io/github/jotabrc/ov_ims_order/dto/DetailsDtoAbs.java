package io.github.jotabrc.ov_ims_order.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class DetailsDtoAbs {

    private final String productUuid;

    @Min(value = 1)
    private final int quantity;
}

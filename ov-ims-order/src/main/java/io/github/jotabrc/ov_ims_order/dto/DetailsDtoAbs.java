package io.github.jotabrc.ov_ims_order.dto;

import io.github.jotabrc.ov_ims_order.validation.StringType;
import io.github.jotabrc.ov_ims_order.validation.ValidString;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class DetailsDtoAbs {

    @ValidString(error = "Invalid Product UUID format", type = StringType.UUID)
    private final String productUuid;

    @Min(value = 1)
    private final int quantity;
}

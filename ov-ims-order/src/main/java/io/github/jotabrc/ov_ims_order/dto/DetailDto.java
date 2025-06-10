package io.github.jotabrc.ov_ims_order.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;

@Getter
public class DetailDto extends DetailsDtoAbs {

    @DecimalMin("0.0")
    private final double unitPrice;

    public DetailDto(String productUuid, int quantity, double unitPrice) {
        super(productUuid, quantity);
        this.unitPrice = unitPrice;
    }
}

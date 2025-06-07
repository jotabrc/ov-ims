package io.github.jotabrc.ov_ims_order.dto;

import lombok.Getter;

@Getter
public class DetailDto extends DetailsDtoAbs {

    private final double unitPrice;

    public DetailDto(String productUuid, int quantity, double unitPrice) {
        super(productUuid, quantity);
        this.unitPrice = unitPrice;
    }
}

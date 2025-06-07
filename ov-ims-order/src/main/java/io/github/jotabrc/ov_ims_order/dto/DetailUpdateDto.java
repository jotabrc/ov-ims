package io.github.jotabrc.ov_ims_order.dto;

import lombok.Getter;

@Getter
public class DetailUpdateDto extends DetailsDtoAbs {

    public DetailUpdateDto(String productUuid, int quantity) {
        super(productUuid, quantity);
    }
}

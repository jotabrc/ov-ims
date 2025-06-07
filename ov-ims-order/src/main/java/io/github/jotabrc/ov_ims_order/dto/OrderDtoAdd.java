package io.github.jotabrc.ov_ims_order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderDtoAdd extends OrderDtoAbs {

    public OrderDtoAdd(String placedBy, List<DetailDto> details) {
        super(placedBy, details);
    }
}

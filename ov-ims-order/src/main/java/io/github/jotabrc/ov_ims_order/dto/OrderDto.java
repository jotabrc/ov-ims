package io.github.jotabrc.ov_ims_order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderDto extends OrderDtoAbs {

    private final String uuid;
    private final double total;

    public OrderDto(String placedBy, double total, List<DetailDto> details, String uuid) {
        super(placedBy, details);
        this.uuid = uuid;
        this.total = total;
    }
}

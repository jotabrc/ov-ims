package io.github.jotabrc.ov_ims_order.dto;

import io.github.jotabrc.ov_ims_order.model.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderUpdateTypeDto {

    private final UpdateType type;
    private final String uuid;
    private final OrderStatus status;
    private final List<DetailUpdateDto> returnItems;
}

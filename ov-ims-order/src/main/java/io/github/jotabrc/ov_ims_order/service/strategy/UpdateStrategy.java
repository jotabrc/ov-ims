package io.github.jotabrc.ov_ims_order.service.strategy;

import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;
import io.github.jotabrc.ov_ims_order.model.Order;

public interface UpdateStrategy {

    void apply(Order order, OrderUpdateTypeDto dto);
}

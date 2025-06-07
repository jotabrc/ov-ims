package io.github.jotabrc.ov_ims_order.service.strategy;

import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;

public interface UpdateStrategyProcessor {

    public UpdateStrategy select(OrderUpdateTypeDto dto);
}

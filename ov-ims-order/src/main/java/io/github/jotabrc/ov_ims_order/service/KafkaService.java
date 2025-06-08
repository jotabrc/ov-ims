package io.github.jotabrc.ov_ims_order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_ims_order.dto.OrderDtoAdd;
import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;
import io.github.jotabrc.ov_ims_order.model.Order;

public interface KafkaService {

    void requestInventoryUpdate(final OrderDtoAdd dto, final Order order, final String type) throws JsonProcessingException;

    void requestInventoryUpdate(final OrderUpdateTypeDto dto, final Order order) throws JsonProcessingException;
}

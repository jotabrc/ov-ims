package io.github.jotabrc.ov_ims_order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_ims_order.dto.OrderDto;
import io.github.jotabrc.ov_ims_order.dto.OrderDtoAdd;
import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;
import io.github.jotabrc.ov_ims_order.dto.PageFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    String save(OrderDtoAdd dto) throws JsonProcessingException;
    void update(OrderUpdateTypeDto dto) throws JsonProcessingException;
    Page<OrderDto> get(PageFilter pageFilter, Pageable pageable);
}

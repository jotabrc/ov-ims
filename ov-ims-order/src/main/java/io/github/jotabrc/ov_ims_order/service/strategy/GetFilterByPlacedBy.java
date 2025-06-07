package io.github.jotabrc.ov_ims_order.service.strategy;

import io.github.jotabrc.ov_ims_order.dto.PageFilter;
import io.github.jotabrc.ov_ims_order.model.Order;
import io.github.jotabrc.ov_ims_order.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class GetFilterByPlacedBy implements GetFilterStrategy {

    @Override
    public Page<Order> filter(final PageFilter pageFilter, final Pageable pageable, final OrderRepository orderRepository) {
        return orderRepository.findByPlacedBy(pageFilter.getPlacedBy(), pageable);
    }
}

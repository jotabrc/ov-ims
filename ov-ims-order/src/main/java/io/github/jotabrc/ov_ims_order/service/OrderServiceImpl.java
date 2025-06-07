package io.github.jotabrc.ov_ims_order.service;

import io.github.jotabrc.ov_ims_order.controller.handler.OrderNotFoundException;
import io.github.jotabrc.ov_ims_order.dto.OrderDto;
import io.github.jotabrc.ov_ims_order.dto.OrderDtoAdd;
import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;
import io.github.jotabrc.ov_ims_order.dto.PageFilter;
import io.github.jotabrc.ov_ims_order.model.Order;
import io.github.jotabrc.ov_ims_order.repository.OrderRepository;
import io.github.jotabrc.ov_ims_order.service.strategy.GetFilterProcessor;
import io.github.jotabrc.ov_ims_order.service.strategy.UpdateStrategyProcessor;
import io.github.jotabrc.ov_ims_order.util.DtoMapper;
import io.github.jotabrc.ov_ims_order.util.EntityCreatorMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final EntityCreatorMapper entityCreatorMapper;
    private final DtoMapper dtoMapper;

    private final UpdateStrategyProcessor updateStrategyProcessor;

    @Transactional
    @Override
    public String save(final OrderDtoAdd dto) {
        Order order = entityCreatorMapper.toEntity(dto);
        setTotal(order);
        return orderRepository.save(order).getUuid();
    }

    @Transactional
    @Override
    public void update(final OrderUpdateTypeDto dto) {
        Order order = getOrElseThrow(dto.getUuid());
        updateStrategyProcessor
                .select(dto)
                .apply(order, dto);
        orderRepository.save(order);
    }

    @Override
    public Page<OrderDto> get(final PageFilter pageFilter, final Pageable pageable) {
        return GetFilterProcessor
                .selector(pageFilter)
                .filter(pageFilter, pageable, orderRepository)
                .map(dtoMapper::toDto);
    }

    private void setTotal(final Order order) {
        order.setTotal(
                order.getDetails()
                        .stream()
                        .map(d -> d.getUnitPrice()
                                .multiply(
                                        BigDecimal.valueOf(d.getQuantity())
                                )
                        )
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    private Order getOrElseThrow(final String uuid) {
        return orderRepository.findByUuid(uuid)
                .orElseThrow(() -> new OrderNotFoundException("Order with UUID %s not found".formatted(uuid)));
    }
}

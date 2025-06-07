package io.github.jotabrc.ov_ims_order.util;

import io.github.jotabrc.ov_ims_order.dto.DetailDto;
import io.github.jotabrc.ov_ims_order.dto.DetailUpdateDto;
import io.github.jotabrc.ov_ims_order.dto.OrderDto;
import io.github.jotabrc.ov_ims_order.model.Detail;
import io.github.jotabrc.ov_ims_order.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoMapperImpl implements DtoMapper {

    @Override
    public OrderDto toDto(Order order) {
        return new OrderDto(
                order.getPlacedBy(),
                order.getTotal().doubleValue(),
                toDto(order.getDetails()),
                order.getUuid()
        );
    }

    @Override
    public DetailDto toDto(Detail detail) {
        return new DetailDto(
                detail.getProductUuid(),
                detail.getQuantity(),
                detail.getUnitPrice().doubleValue()
        );
    }

    @Override
    public DetailDto toDtoReturnType(final Detail detail, final DetailUpdateDto dto) {
        return new DetailDto(
                detail.getProductUuid(),
                -dto.getQuantity(),
                -detail.getUnitPrice().doubleValue()
        );
    }

    @Override
    public List<DetailDto> toDto(List<Detail> details) {
        return details
                .stream()
                .map(this::toDto)
                .toList();
    }
}

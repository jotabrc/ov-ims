package io.github.jotabrc.ov_ims_order.util;

import io.github.jotabrc.ov_ims_order.dto.DetailDto;
import io.github.jotabrc.ov_ims_order.dto.OrderDtoAdd;
import io.github.jotabrc.ov_ims_order.model.Detail;
import io.github.jotabrc.ov_ims_order.model.Order;
import io.github.jotabrc.ov_ims_order.model.OrderStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class EntityCreatorMapperImpl implements EntityCreatorMapper {

    @Override
    public Order toEntity(final OrderDtoAdd dto) {
        return Order
                .builder()
                .uuid(UUID.randomUUID().toString())
                .placedBy(dto.getPlacedBy())
                .status(OrderStatus.PLACED)
                .details(dto.getDetails().stream()
                        .map(this::toEntity)
                        .toList())
                .build();
    }

    @Override
    public Detail toEntity(final DetailDto dto) {
        return Detail
                .builder()
                .productUuid(dto.getProductUuid())
                .unitPrice(BigDecimal.valueOf(dto.getUnitPrice()))
                .quantity(dto.getQuantity())
                .build();
    }

    @Override
    public List<Detail> toEntity(final List<DetailDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .toList();
    }
}

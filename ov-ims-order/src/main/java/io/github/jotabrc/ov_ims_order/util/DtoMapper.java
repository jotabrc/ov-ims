package io.github.jotabrc.ov_ims_order.util;

import io.github.jotabrc.ov_ims_order.dto.*;
import io.github.jotabrc.ov_ims_order.model.Detail;
import io.github.jotabrc.ov_ims_order.model.Order;

import java.util.List;

public interface DtoMapper {

    OrderDto toDto(Order order);
    DetailDto toDto(Detail detail);
    DetailDto toDtoReturnType(Detail detail, DetailUpdateDto dto);
    List<DetailDto> toDto(List<Detail> details);
    ProductDtoUuidAndQuantity toDto(DetailDto dto);
    OrderDtoKafka toDto(String uuid, String type, OrderDtoAdd dto);
    OrderDtoKafka toDto(String uuid, String type, OrderUpdateTypeDto dto);
    ProductDtoUuidAndQuantity toDto(DetailUpdateDto dto);
}

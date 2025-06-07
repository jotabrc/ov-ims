package io.github.jotabrc.ov_ims_order.util;

import io.github.jotabrc.ov_ims_order.dto.DetailDto;
import io.github.jotabrc.ov_ims_order.dto.DetailUpdateDto;
import io.github.jotabrc.ov_ims_order.dto.OrderDto;
import io.github.jotabrc.ov_ims_order.model.Detail;
import io.github.jotabrc.ov_ims_order.model.Order;

import java.util.List;

public interface DtoMapper {

    OrderDto toDto(Order order);
    DetailDto toDto(Detail detail);
    DetailDto toReturnDto(Detail detail, DetailUpdateDto dto);
    List<DetailDto> toDto(List<Detail> details);
}

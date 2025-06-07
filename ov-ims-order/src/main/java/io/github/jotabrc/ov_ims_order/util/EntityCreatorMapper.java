package io.github.jotabrc.ov_ims_order.util;

import io.github.jotabrc.ov_ims_order.dto.DetailDto;
import io.github.jotabrc.ov_ims_order.dto.OrderDtoAdd;
import io.github.jotabrc.ov_ims_order.model.Detail;
import io.github.jotabrc.ov_ims_order.model.Order;

import java.util.List;

public interface EntityCreatorMapper {

    Order toEntity(OrderDtoAdd dto);
    Detail toEntity(DetailDto dto);
    List<Detail> toEntity(List<DetailDto> dtos);
}

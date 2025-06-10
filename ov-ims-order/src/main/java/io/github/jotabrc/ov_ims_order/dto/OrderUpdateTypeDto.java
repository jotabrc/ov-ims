package io.github.jotabrc.ov_ims_order.dto;

import io.github.jotabrc.ov_ims_order.model.OrderStatus;
import io.github.jotabrc.ov_ims_order.validation.StringType;
import io.github.jotabrc.ov_ims_order.validation.ValidString;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderUpdateTypeDto {

    private final UpdateType type;

    @ValidString(error = "Invalid Order UUID format", type = StringType.UUID)
    private final String uuid;
    private final OrderStatus status;

    @Valid
    private final List<DetailUpdateDto> returnItems;
}

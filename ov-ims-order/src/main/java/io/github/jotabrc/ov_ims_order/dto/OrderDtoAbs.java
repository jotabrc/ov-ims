package io.github.jotabrc.ov_ims_order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
public abstract class OrderDtoAbs {

    @NotNull
    private final String placedBy;

    @NotNull
    private final List<DetailDto> details;
}

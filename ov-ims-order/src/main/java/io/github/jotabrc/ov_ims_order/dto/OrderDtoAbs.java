package io.github.jotabrc.ov_ims_order.dto;

import io.github.jotabrc.ov_ims_order.validation.StringType;
import io.github.jotabrc.ov_ims_order.validation.ValidString;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
public abstract class OrderDtoAbs {

    @NotNull @ValidString(error = "Invalid UUID format for placeBy field", type = StringType.UUID)
    private final String placedBy;

    @NotNull @Valid
    private final List<DetailDto> details;
}

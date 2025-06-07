package io.github.jotabrc.ov_ims_order.service.strategy;

import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;
import io.github.jotabrc.ov_ims_order.model.Order;
import io.github.jotabrc.ov_ims_order.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static io.github.jotabrc.ov_ims_order.model.OrderStatus.*;

@Validated
@Component
@AllArgsConstructor
public class UpdateStrategyNewStatus implements UpdateStrategy {

    @Override
    public void apply(Order order, OrderUpdateTypeDto dto) {
        updateOrThrow(order, dto);
    }

    private void updateOrThrow(final Order order, OrderUpdateTypeDto dto) {
        if (isNewStatusAvailable(order.getStatus(), dto.getStatus()))
            order.setStatus(dto.getStatus());
        else throw new IllegalStateException(
                "Order %s with status (%s) cannot update to (%s). Allowed updates: [%s]"
                        .formatted(order.getUuid(), order.getStatus(), dto.getStatus(), allowedUpdates(order.getStatus()))
        );
    }

    private boolean isNewStatusAvailable(@NotNull final OrderStatus status, @NotNull final OrderStatus newStatus) {
        return allowedUpdates(status).contains(newStatus);
    }

    private List<OrderStatus> allowedUpdates(OrderStatus status) {
        return switch (status) {
            case PLACED -> List.of(HAS_INVENTORY);
            case HAS_INVENTORY -> List.of(PROCESSING);
            case PROCESSING -> List.of(IN_DELIVERY);
            case IN_DELIVERY -> List.of(DELIVERED);
            case null, default -> List.of();
        };
    }
}

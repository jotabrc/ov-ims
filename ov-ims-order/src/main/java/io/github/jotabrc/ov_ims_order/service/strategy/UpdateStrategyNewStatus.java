package io.github.jotabrc.ov_ims_order.service.strategy;

import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;
import io.github.jotabrc.ov_ims_order.model.Order;
import io.github.jotabrc.ov_ims_order.model.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
        else throw new IllegalStateException("Order ineligible for  status %s".formatted(dto.getStatus()));
    }

    private boolean isNewStatusAvailable(final OrderStatus status, final OrderStatus newStatus) {
        return switch (status) {
            case PLACED -> switch (newStatus) {
                case HAS_INVENTORY -> true;
                case null, default -> false;
            };
            case HAS_INVENTORY -> switch (newStatus) {
                case PROCESSING -> true;
                case null, default -> false;
            };
            case PROCESSING -> switch (newStatus) {
                case IN_DELIVERY -> true;
                case null, default -> false;
            };
            case IN_DELIVERY -> switch (newStatus) {
                case DELIVERED -> true;
                case null, default -> false;
            };
            case null, default -> false;
        };
    }
}

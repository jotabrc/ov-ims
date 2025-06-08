package io.github.jotabrc.ov_ims_order.service.strategy;

import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;
import io.github.jotabrc.ov_ims_order.model.Order;
import io.github.jotabrc.ov_ims_order.model.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateStrategyCancellation implements UpdateStrategy {

    @Override
    public void apply(Order order, OrderUpdateTypeDto dto) {
        cancelOrThrow(order, dto.getStatus());
    }

    private void cancelOrThrow(final Order order, final OrderStatus status) {
        if (!isCancellationAvailable(order.getStatus()))
            throw new IllegalStateException(
                    "Order %s with status %s ineligible for cancellation"
                    .formatted(order.getUuid(), order.getStatus())
            );

        if (!status.equals(OrderStatus.CANCELLED))
            throw new IllegalStateException(
                    "Invalid status update: Expected CANCELLED, received %s"
                            .formatted(status)
            );

        order.setStatus(OrderStatus.CANCELLED);
    }

    private boolean isCancellationAvailable(final OrderStatus status) {
        return switch (status) {
            case PLACED, HAS_INVENTORY, PROCESSING, READY_TO_DELIVERY -> true;
            case null, default -> false;
        };
    }
}

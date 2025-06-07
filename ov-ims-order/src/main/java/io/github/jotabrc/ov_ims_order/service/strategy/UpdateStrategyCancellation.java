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
            throw new IllegalStateException("Order ineligible for cancellation");
        else if (!status.equals(OrderStatus.CANCELLED))
            throw new IllegalStateException("New Order Status is incompatible with request, for cancellation CANCEL status is required");
        order.setStatus(OrderStatus.CANCELLED);
    }

    private boolean isCancellationAvailable(final OrderStatus status) {
        return switch (status) {
            case PLACED, PROCESSING, HAS_INVENTORY -> true;
            case null, default -> false;
        };
    }
}

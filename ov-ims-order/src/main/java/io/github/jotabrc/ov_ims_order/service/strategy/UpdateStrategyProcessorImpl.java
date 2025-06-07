package io.github.jotabrc.ov_ims_order.service.strategy;

import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateStrategyProcessorImpl implements UpdateStrategyProcessor {

    private final UpdateStrategy updateStrategyCancellation;
    private final UpdateStrategy updateStrategyReturning;
    private final UpdateStrategy updateStrategyNewStatus;


    public UpdateStrategy select(OrderUpdateTypeDto dto) {
        return switch (dto.getType()) {
            case RETURN -> updateStrategyReturning;
            case CANCEL -> updateStrategyCancellation;
            case NEW_STATUS -> updateStrategyNewStatus;
            case null, default -> throw new IllegalStateException("Update Strategy not supported");
        };
    }
}

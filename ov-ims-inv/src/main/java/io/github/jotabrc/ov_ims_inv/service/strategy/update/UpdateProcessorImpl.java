package io.github.jotabrc.ov_ims_inv.service.strategy.update;

import io.github.jotabrc.ov_ims_inv.dto.UpdateType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor
public class UpdateProcessorImpl implements UpdateProcessor {

    private final UpdateStrategyReserveAdd updateStrategyReserveAdd;
    private final UpdateStrategyReserveRemove updateStrategyReserveRemove;
    private final UpdateStrategyInventoryRemoveFromReserve updateStrategyInventoryRemoveFromReserve;
    private final UpdateStrategyInventoryAdd updateStrategyInventoryAdd;

    @Override
    public UpdateStrategy select(UpdateType type) {
        return switch (type) {
            case RESERVE_ADD -> updateStrategyReserveAdd;
            case RESERVE_REMOVE -> updateStrategyReserveRemove;
            case INVENTORY_REMOVE_FROM_RESERVE -> updateStrategyInventoryRemoveFromReserve;
            case INVENTORY_ADD -> updateStrategyInventoryAdd;
            case null, default -> throw new IllegalStateException("Update type not supported");
        };
    }
}

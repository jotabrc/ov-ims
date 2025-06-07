package io.github.jotabrc.ov_ims_inv.util;

import io.github.jotabrc.ov_ims_inv.model.Inventory;
import org.springframework.stereotype.Component;

@Component
public class EntityCreatorMapperImpl implements EntityCreatorMapper {

    @Override
    public Inventory toEntity(String productUuid) {
        return Inventory
                .builder()
                .productUuid(productUuid)
                .inventory(0)
                .reservedInventory(0)
                .build();
    }
}

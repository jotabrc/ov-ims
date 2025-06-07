package io.github.jotabrc.ov_ims_inv.util;

import io.github.jotabrc.ov_ims_inv.model.Inventory;

public interface EntityCreatorMapper {

    Inventory toEntity(String productUuid);
}

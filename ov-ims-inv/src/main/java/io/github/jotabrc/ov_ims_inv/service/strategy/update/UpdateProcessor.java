package io.github.jotabrc.ov_ims_inv.service.strategy.update;

import io.github.jotabrc.ov_ims_inv.dto.UpdateType;

public interface UpdateProcessor {

    public UpdateStrategy select(UpdateType type);
}

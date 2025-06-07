package io.github.jotabrc.ov_ims_order.service.strategy;

import io.github.jotabrc.ov_ims_order.dto.PageFilter;

public class GetFilterProcessor {

    public static GetFilterStrategy selector(final PageFilter pageFilter) {
        if (pageFilter.getUuid() != null) {
            return new GetFilterByUuid();
        } else if (pageFilter.getPlacedBy() != null) {
            return new GetFilterByPlacedBy();
        } else {
            throw new IllegalArgumentException("Filter not found, to retrieve order information an Order UUID or User UUID is required");
        }
    }
}

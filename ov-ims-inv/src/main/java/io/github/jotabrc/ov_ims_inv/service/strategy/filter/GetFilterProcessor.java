package io.github.jotabrc.ov_ims_inv.service.strategy.filter;

import io.github.jotabrc.ov_ims_inv.dto.PageFilter;

public interface GetFilterProcessor<T> {

    GetFilterStrategy<T> select(PageFilter pageFilter);
}

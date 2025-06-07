package io.github.jotabrc.ov_ims_inv.service.strategy.filter;

import io.github.jotabrc.ov_ims_inv.dto.PageFilter;
import org.springframework.data.domain.Pageable;

public interface GetFilterStrategy<T> {

    Object filter(
            PageFilter pageFilter,
            Pageable pageable
    );
}

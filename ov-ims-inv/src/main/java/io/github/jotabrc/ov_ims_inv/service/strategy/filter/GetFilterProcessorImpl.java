package io.github.jotabrc.ov_ims_inv.service.strategy.filter;

import io.github.jotabrc.ov_ims_inv.dto.InventoryDto;
import io.github.jotabrc.ov_ims_inv.dto.PageFilter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor
public class GetFilterProcessorImpl implements GetFilterProcessor<InventoryDto> {

    private final GetFilterStrategyBetween getFilterStrategyBetween;
    private final GetFilterStrategyGreaterThan getFilterStrategyGreaterThan;
    private final GetFilterStrategyLessThan getFilterStrategyLessThan;
    private final GetFilterStrategyProductUuid getFilterStrategyProductUuid;

    @Override
    public GetFilterStrategy<InventoryDto> select(final PageFilter pageFilter) {
        if (pageFilter.getProductUuid() != null) return getFilterStrategyProductUuid;
        else if (pageFilter.getMinValue() >= 0 && pageFilter.getMaxValue() > pageFilter.getMinValue()) return getFilterStrategyBetween;
        else if (pageFilter.getMinValue() >= 0) return getFilterStrategyGreaterThan;
        else if (pageFilter.getMaxValue() > 0) return getFilterStrategyLessThan;
        else throw new IllegalStateException("Filter strategy not supported");
    }
}

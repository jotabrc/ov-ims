package io.github.jotabrc.ov_ims_inv.service.strategy.filter;

import io.github.jotabrc.ov_ims_inv.dto.InventoryDto;
import io.github.jotabrc.ov_ims_inv.dto.PageFilter;
import io.github.jotabrc.ov_ims_inv.repository.InventoryRepository;
import io.github.jotabrc.ov_ims_inv.util.DtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor
public class GetFilterStrategyGreaterThan implements GetFilterStrategy<InventoryDto> {

    private final InventoryRepository inventoryRepository;
    private final DtoMapper dtoMapper;

    @Override
    public Page<InventoryDto> filter(
            final PageFilter pageFilter,
            final Pageable pageable
    ) {
        return inventoryRepository.findByInventoryGreaterThan(
                pageFilter.getMinValue(),
                pageable
        )
                .map(dtoMapper::toDto);
    }
}

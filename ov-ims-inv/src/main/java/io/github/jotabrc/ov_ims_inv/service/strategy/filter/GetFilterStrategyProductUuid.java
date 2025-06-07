package io.github.jotabrc.ov_ims_inv.service.strategy.filter;

import io.github.jotabrc.ov_ims_inv.controller.handler.ProductNotFoundException;
import io.github.jotabrc.ov_ims_inv.dto.InventoryDto;
import io.github.jotabrc.ov_ims_inv.dto.PageFilter;
import io.github.jotabrc.ov_ims_inv.model.Inventory;
import io.github.jotabrc.ov_ims_inv.repository.InventoryRepository;
import io.github.jotabrc.ov_ims_inv.util.DtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor
public class GetFilterStrategyProductUuid implements GetFilterStrategy<InventoryDto> {

    private final InventoryRepository inventoryRepository;
    private final DtoMapper dtoMapper;

    @Override
    public InventoryDto filter(
            final PageFilter pageFilter,
            final Pageable ignored
    ) {
        Inventory inventory = getOrElseThrow(pageFilter);
        return dtoMapper.toDto(inventory);
    }

    private Inventory getOrElseThrow(PageFilter pageFilter) {
        return inventoryRepository.findByProductUuid(pageFilter.getProductUuid())
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product %s not found"
                                .formatted(pageFilter.getProductUuid())
                ));
    }
}

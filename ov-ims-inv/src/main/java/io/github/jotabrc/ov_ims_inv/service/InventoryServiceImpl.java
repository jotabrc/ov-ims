package io.github.jotabrc.ov_ims_inv.service;

import io.github.jotabrc.ov_ims_inv.controller.handler.ProductAlreadyExistsException;
import io.github.jotabrc.ov_ims_inv.controller.handler.ProductNotFoundException;
import io.github.jotabrc.ov_ims_inv.dto.InventoryDto;
import io.github.jotabrc.ov_ims_inv.dto.InventoryDtoUpdate;
import io.github.jotabrc.ov_ims_inv.dto.PageFilter;
import io.github.jotabrc.ov_ims_inv.model.Inventory;
import io.github.jotabrc.ov_ims_inv.repository.InventoryRepository;
import io.github.jotabrc.ov_ims_inv.service.strategy.filter.GetFilterProcessor;
import io.github.jotabrc.ov_ims_inv.service.strategy.update.UpdateProcessor;
import io.github.jotabrc.ov_ims_inv.util.EntityCreatorMapper;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final UpdateProcessor updateProcessor;
    private final EntityCreatorMapper entityCreatorMapper;
    private final GetFilterProcessor<InventoryDto> getFilterProcessor;

    @Override
    public void save(final String productUuid) {
        ifExistsThrow(productUuid);
        Inventory inventory = entityCreatorMapper.toEntity(productUuid);
        inventoryRepository.save(inventory);
    }

    @Override
    public void update(final InventoryDtoUpdate dto) {
        ifRequestedAmountIsZeroOrLessThrow(dto);
        Inventory inventory = getOrElseThrow(dto);
        updateProcessor
                .select(dto.getType())
                .update(inventory, dto);
        inventoryRepository.save(inventory);
    }

    @Override
    public Object get(PageFilter pageFilter, Pageable pageable) {
        return getFilterProcessor
                .select(pageFilter)
                .filter(pageFilter, pageable);
    }

    private void ifExistsThrow(@NotNull final String productUuid) {
        boolean exists = inventoryRepository.existsByProductUuid(productUuid);
        if (exists)
            throw new ProductAlreadyExistsException("Product %s already exists, duplicates are not allowed"
                    .formatted(productUuid));
    }

    private void ifRequestedAmountIsZeroOrLessThrow(InventoryDtoUpdate dto) {
        if (dto.getData().getInventory() <= 0)
            throw new IllegalStateException(
                    "Cannot place inventory update with quantity amount of (%d) "
                            .formatted(dto.getData().getInventory())
            );
    }

    private Inventory getOrElseThrow(InventoryDtoUpdate dto) {
        return inventoryRepository
                .findByProductUuid(dto.getData().getProductUuid())
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product %s not found".formatted(dto.getData().getProductUuid())
                        )
                );
    }
}

package io.github.jotabrc.ov_ims_inv.service;

import io.github.jotabrc.ov_ims_inv.controller.handler.InventoryUpdateException;
import io.github.jotabrc.ov_ims_inv.controller.handler.ProductAlreadyExistsException;
import io.github.jotabrc.ov_ims_inv.dto.InventoryDto;
import io.github.jotabrc.ov_ims_inv.dto.PageFilter;
import io.github.jotabrc.ov_ims_inv.logging.Log;
import io.github.jotabrc.ov_ims_inv.model.Inventory;
import io.github.jotabrc.ov_ims_inv.repository.InventoryRepository;
import io.github.jotabrc.ov_ims_inv.service.strategy.filter.GetFilterProcessor;
import io.github.jotabrc.ov_ims_inv.util.MapperService;
import io.github.jotabrc.ov_ims_inv.validation.StringType;
import io.github.jotabrc.ov_ims_inv.validation.ValidString;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final MapperService mapperService;
    private final GetFilterProcessor<InventoryDto> getFilterProcessor;

    @Async
    @Log
    @Override
    public void save(
            @ValidString(error = "Invalid Product UUID format", type = StringType.UUID)
            final String productUuid
    ) {
        int retries = 0;
        while (retries < 5) {
            try {
                ifExistsThrow(productUuid);
                Inventory inventory = mapperService.transform(productUuid, null, Inventory.class);
                inventoryRepository.save(inventory);
                return;
            } catch (Exception e) {
                log.error("Failed to insert new Product ({}) in attempt ({}): {}", productUuid, retries, e.getMessage());
                retries++;
            }
        }
        throw new InventoryUpdateException(
                "Insertion of new Product failed: Product (%s)"
                        .formatted(productUuid)
        );
    }

    @Log
    @Override
    public Object get(PageFilter pageFilter, Pageable pageable) {
        return getFilterProcessor
                .select(pageFilter)
                .filter(pageFilter, pageable);
    }

    private void ifExistsThrow(final String productUuid) {
        boolean exists = inventoryRepository.existsByProductUuid(productUuid);
        if (exists)
            throw new ProductAlreadyExistsException("Product %s already exists, duplicates are not allowed"
                    .formatted(productUuid));
    }
}

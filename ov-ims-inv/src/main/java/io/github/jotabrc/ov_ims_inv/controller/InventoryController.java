package io.github.jotabrc.ov_ims_inv.controller;

import io.github.jotabrc.ov_ims_inv.dto.InventoryDtoUpdate;
import io.github.jotabrc.ov_ims_inv.dto.PageFilter;
import io.github.jotabrc.ov_ims_inv.service.InventoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping(path = "/inventory", produces = MediaType.APPLICATION_JSON_VALUE)
public class InventoryController {

    private final InventoryService inventoryService;

    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody final InventoryDtoUpdate dto) {
        inventoryService.update(dto);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping
    public ResponseEntity<Object> get(
            @RequestParam(required = false) final String productUuid,
            @RequestParam(required = false) final Integer minValue,
            @RequestParam(required = false) final Integer maxValue,
            @NotNull final Pageable pageable
            ) {
        PageFilter pageFilter = PageFilter
                .builder()
                .productUuid(productUuid)
                .minValue(minValue)
                .maxValue(maxValue)
                .build();
        Object result = inventoryService.get(pageFilter, pageable);
        return ResponseEntity
                .ok(result);
    }
}

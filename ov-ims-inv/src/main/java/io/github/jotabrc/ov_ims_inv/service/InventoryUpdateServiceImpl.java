package io.github.jotabrc.ov_ims_inv.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_ims_inv.controller.handler.ProductNotFoundException;
import io.github.jotabrc.ov_ims_inv.dto.*;
import io.github.jotabrc.ov_ims_inv.kafka.KafkaProducer;
import io.github.jotabrc.ov_ims_inv.model.Inventory;
import io.github.jotabrc.ov_ims_inv.repository.InventoryRepository;
import io.github.jotabrc.ov_ims_inv.service.strategy.update.UpdateProcessor;
import io.github.jotabrc.ov_ims_inv.util.MapperService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class InventoryUpdateServiceImpl implements InventoryUpdateService {

    private final InventoryRepository inventoryRepository;
    private final UpdateProcessor updateProcessor;
    private final MapperService mapperService;
    private final KafkaProducer kafkaProducer;

    @Override
    public void update(final InventoryDtoUpdate dto) {
        ifRequestedAmountIsZeroOrLessThrow(dto);
        Inventory inventory = getOrElseThrow(dto);
        updateProcessor
                .select(dto.getType())
                .update(inventory, dto);
        inventoryRepository.save(inventory);
    }

    @Transactional
    @Override
    public void update(final OrderDtoKafka orderDto) throws JsonProcessingException {
        boolean isReserved = false;
        try {
            Map<Inventory, InventoryDtoUpdate> inventories = getUpdateMap(orderDto.getDetails(), orderDto.getType());
            inventories.forEach((inventory, dto) ->
                    updateProcessor
                            .select(dto.getType())
                            .update(inventory, dto)
            );
            inventoryRepository.saveAll(inventories.keySet());
            isReserved = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (orderDto.getType().equals(UpdateType.RESERVE_ADD))
                replyReservedRequiredRequest(orderDto, isReserved);
        }
    }

    private void ifRequestedAmountIsZeroOrLessThrow(final InventoryDtoUpdate dto) {
        if (dto.getData().getInventory() <= 0)
            throw new IllegalStateException(
                    "Cannot place inventory update with quantity amount of (%d) "
                            .formatted(dto.getData().getInventory())
            );
    }

    private Inventory getOrElseThrow(final InventoryDtoUpdate dto) {
        return inventoryRepository
                .findByProductUuid(dto.getData().getProductUuid())
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product %s not found".formatted(dto.getData().getProductUuid())
                        )
                );
    }

    private Map<Inventory, InventoryDtoUpdate> getUpdateMap(final List<ProductDtoUuidAndQuantity> dtos, final UpdateType type) {
        return dtos.stream()
                .map(dto -> mapperService.transform(dto, type, InventoryDtoUpdate.class))
                .filter(dto -> {
                    ifRequestedAmountIsZeroOrLessThrow(dto);
                    return true;
                })
                .collect(Collectors.toMap(
                        this::getOrElseThrow,
                        inventoryDtoUpdate -> inventoryDtoUpdate
                ));
    }

    private void replyReservedRequiredRequest(OrderDtoKafka orderDto, boolean isReserved) throws JsonProcessingException {
        OrderDtoReservedMessage message = kafkaProducer.buildReservedMessage(orderDto.getUuid(), isReserved);
        kafkaProducer.produce(message, "RESERVE_REPLY");
    }
}

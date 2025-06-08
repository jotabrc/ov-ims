package io.github.jotabrc.ov_ims_inv.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jotabrc.ov_ims_inv.dto.OrderDtoKafka;
import io.github.jotabrc.ov_ims_inv.dto.ProductDtoUuidOnly;
import io.github.jotabrc.ov_ims_inv.service.InventoryService;
import io.github.jotabrc.ov_ims_inv.service.InventoryUpdateService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor
public class KafkaEventConsumer {

    private final InventoryService inventoryService;
    private final InventoryUpdateService inventoryUpdateService;

    @KafkaListener(topics = {"PRODUCT_NEW"},
            containerFactory = "kafkaListenerContainerFactory")
    public void productNew(ConsumerRecord<String, String> record) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductDtoUuidOnly productDtoUuidOnly = objectMapper.readValue(record.value(), ProductDtoUuidOnly.class);

        inventoryService.save(productDtoUuidOnly.getUuid());
    }

    @KafkaListener(topics = {
            "RESERVE_ADD",
            "RESERVE_REMOVE",
            "INVENTORY_REMOVE_FROM_RESERVE",
            "INVENTORY_ADD"

    },
            containerFactory = "kafkaListenerContainerFactory")
    public void inventoryUpdate(ConsumerRecord<String, String> record) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        OrderDtoKafka orderDtoKafka = objectMapper.readValue(record.value(), OrderDtoKafka.class);
        inventoryUpdateService.update(orderDtoKafka);
    }
}

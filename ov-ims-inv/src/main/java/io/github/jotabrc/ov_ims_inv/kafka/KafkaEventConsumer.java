package io.github.jotabrc.ov_ims_inv.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jotabrc.ov_ims_inv.config.RedisConfig;
import io.github.jotabrc.ov_ims_inv.dto.OrderDtoKafka;
import io.github.jotabrc.ov_ims_inv.dto.ProductDtoUuidOnly;
import io.github.jotabrc.ov_ims_inv.service.InventoryService;
import io.github.jotabrc.ov_ims_inv.service.InventoryUpdateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component @AllArgsConstructor
public class KafkaEventConsumer {

    private final InventoryService inventoryService;
    private final InventoryUpdateService inventoryUpdateService;
    private final RedisConfig redisConfig;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = {"PRODUCT_NEW"},
            containerFactory = "kafkaListenerContainerFactory")
    public void productNew(ConsumerRecord<String, String> record) {
        try {
            ProductDtoUuidOnly productDtoUuidOnly = getProductDtoUuidOnly(record);
            if (productDtoUuidOnly == null) return;
            inventoryService.save(productDtoUuidOnly.getUuid());
        } catch (JsonProcessingException e) {
            log.error("Topic received ({}) deserialization error: {}", record.topic(), record.value(), e);
        }
    }

    @KafkaListener(topics = {
            "RESERVE_ADD",
            "RESERVE_REMOVE",
            "INVENTORY_REMOVE_FROM_RESERVE",
            "INVENTORY_ADD"

    },
            containerFactory = "kafkaListenerContainerFactory")
    public void inventoryUpdate(ConsumerRecord<String, String> record) {
        try {
            OrderDtoKafka orderDtoKafka = getOrderDtoKafka(record);
            if (orderDtoKafka == null) return;
            inventoryUpdateService.update(orderDtoKafka);
        } catch (JsonProcessingException e) {
            log.error("Topic received ({}) deserialization error: {}", record.topic(), record.value(), e);
        }
    }

    private ProductDtoUuidOnly getProductDtoUuidOnly(ConsumerRecord<String, String> record) throws JsonProcessingException {
        ProductDtoUuidOnly productDtoUuidOnly = objectMapper.readValue(record.value(), ProductDtoUuidOnly.class);
        if (messageProcessingLimiter(productDtoUuidOnly.getUuid(), productDtoUuidOnly)) return null;
        return productDtoUuidOnly;
    }

    private OrderDtoKafka getOrderDtoKafka(ConsumerRecord<String, String> record) throws JsonProcessingException {
        OrderDtoKafka orderDtoKafka = objectMapper.readValue(record.value(), OrderDtoKafka.class);
        if (messageProcessingLimiter(orderDtoKafka.getUuid(), orderDtoKafka)) return null;
        return orderDtoKafka;
    }

    private <T> boolean messageProcessingLimiter(String key, T value) {
        Boolean firstAttempt = redisConfig
                .redisTemplate()
                .opsForValue()
                .setIfAbsent(key, value);
        if (Boolean.FALSE.equals(firstAttempt)) {
            log.warn("Message already processed for key: {}", key);
            return true;
        }
        return false;
    }
}

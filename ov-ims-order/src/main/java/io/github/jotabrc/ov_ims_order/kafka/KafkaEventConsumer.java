package io.github.jotabrc.ov_ims_order.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jotabrc.ov_ims_order.config.RedisConfig;
import io.github.jotabrc.ov_ims_order.dto.OrderDtoReservedMessage;
import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;
import io.github.jotabrc.ov_ims_order.dto.UpdateType;
import io.github.jotabrc.ov_ims_order.model.OrderStatus;
import io.github.jotabrc.ov_ims_order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class KafkaEventConsumer {

    private final OrderService orderService;
    private final RedisConfig redisConfig;

    @KafkaListener(topics = {"RESERVE_REPLY"},
            containerFactory = "kafkaListenerContainerFactory")
    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 1500, multiplier = 1.5)
    )
    public void listener(ConsumerRecord<String, String> record) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderDtoReservedMessage message = objectMapper.readValue(record.value(), OrderDtoReservedMessage.class);

            buildReaction(message);
        } catch (JsonProcessingException e) {
            log.error("Message deserialization error: {}", record.value(), e);
        }
    }

    @Async
    private void buildReaction(final OrderDtoReservedMessage message) throws JsonProcessingException {
        if (messageProcessingLimiter(message)) return;
        OrderUpdateTypeDto dto = OrderUpdateTypeDto
                .builder()
                .type(message.isReserved() ? UpdateType.NEW_STATUS : UpdateType.CANCEL)
                .uuid(message.getUuid())
                .status(message.isReserved() ? OrderStatus.HAS_INVENTORY : OrderStatus.CANCELLED)
                .build();

        orderService.update(dto);
    }

    private boolean messageProcessingLimiter(OrderDtoReservedMessage message) {
        Boolean firstAttempt = redisConfig
                .redisTemplate()
                .opsForValue()
                .setIfAbsent(message.getUuid(), message);
        if (Boolean.FALSE.equals(firstAttempt)) {
            log.warn("Message already processed for message: {}", message.getUuid());
            return true;
        }
        return false;
    }
}

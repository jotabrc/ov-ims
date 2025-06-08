package io.github.jotabrc.ov_ims_order.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jotabrc.ov_ims_order.dto.OrderDtoReservedMessage;
import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;
import io.github.jotabrc.ov_ims_order.dto.UpdateType;
import io.github.jotabrc.ov_ims_order.model.OrderStatus;
import io.github.jotabrc.ov_ims_order.service.OrderService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaEventConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = {"RESERVE_REPLY"},
            containerFactory = "kafkaListenerContainerFactory")
    public void listener(ConsumerRecord<String, String> record) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        OrderDtoReservedMessage message = objectMapper.readValue(record.value(), OrderDtoReservedMessage.class);

        buildReaction(message);
    }

    @Async
    private void buildReaction(final OrderDtoReservedMessage message) throws JsonProcessingException {
        OrderUpdateTypeDto orderUpdateTypeDto;
        UpdateType type;
        OrderStatus status;

        if (message.isReserved()) {
            type = UpdateType.NEW_STATUS;
            status = OrderStatus.HAS_INVENTORY;
        } else {
            type = UpdateType.CANCEL;
            status = OrderStatus.CANCELLED;
        }

        orderUpdateTypeDto = OrderUpdateTypeDto
                .builder()
                .type(type)
                .uuid(message.getUuid())
                .status(status)
                .build();

        orderService.update(orderUpdateTypeDto);
    }
}

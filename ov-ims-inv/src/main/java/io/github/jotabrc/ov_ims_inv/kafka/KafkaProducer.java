package io.github.jotabrc.ov_ims_inv.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jotabrc.ov_ims_inv.dto.OrderDtoReservedMessage;
import io.github.jotabrc.ov_ims_inv.util.DtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final DtoMapper dtoMapper;

    @Async
    public <T> void produce(T t, String topic) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(t);
        kafkaTemplate.send(topic, json);
    }

    public OrderDtoReservedMessage buildReservedMessage(final String uuid, final boolean isReserved) {
        return new OrderDtoReservedMessage(uuid, isReserved);
    }
}

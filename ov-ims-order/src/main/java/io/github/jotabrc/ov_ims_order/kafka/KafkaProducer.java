package io.github.jotabrc.ov_ims_order.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jotabrc.ov_ims_order.util.DtoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@AllArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final DtoMapper dtoMapper;

    @Async
    public <T> void produce(T t, @NotNull String topic) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(t);
        kafkaTemplate.send(topic, json);
    }
}

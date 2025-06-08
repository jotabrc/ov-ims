package io.github.jotabrc.ov_ims_product.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jotabrc.ov_ims_product.dto.ProductDtoUuidOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Async
    public <T> void produce(T t, String topic) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(t);
        kafkaTemplate.send(topic, json);
    }

    public <T> ProductDtoUuidOnly build(final String productUuid) {
        return new ProductDtoUuidOnly(productUuid);
    }
}

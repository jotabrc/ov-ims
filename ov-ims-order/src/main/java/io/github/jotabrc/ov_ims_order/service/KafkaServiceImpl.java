package io.github.jotabrc.ov_ims_order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_ims_order.dto.OrderDtoAdd;
import io.github.jotabrc.ov_ims_order.dto.OrderDtoKafka;
import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;
import io.github.jotabrc.ov_ims_order.kafka.KafkaProducer;
import io.github.jotabrc.ov_ims_order.model.Order;
import io.github.jotabrc.ov_ims_order.model.OrderStatus;
import io.github.jotabrc.ov_ims_order.util.DtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaProducer kafkaProducer;
    private final DtoMapper dtoMapper;

    @Override
    public void requestInventoryUpdate(final OrderDtoAdd dto, final Order order, final String type) throws JsonProcessingException {
        OrderDtoKafka orderDtoKafka = dtoMapper.toDto(order.getUuid(), type, dto);
        kafkaProducer.produce(orderDtoKafka, type);
    }

    @Override
    public void requestInventoryUpdate(final OrderUpdateTypeDto dto, final Order order) throws JsonProcessingException {
        String type = null;
        switch (dto.getType()) {
            case CANCEL -> type = "RESERVE_REMOVE";
            case RETURN -> type = "INVENTORY_ADD";
            case NEW_STATUS -> {
                if (dto.getStatus().equals(OrderStatus.PROCESSING)) type = "INVENTORY_REMOVE_FROM_RESERVE";
            }
        }

        if (type == null || dto.getReturnItems() == null) return;

        OrderDtoKafka orderDtoKafka = dtoMapper.toDto(order.getUuid(), type, dto);
        kafkaProducer.produce(orderDtoKafka, type);
    }
}

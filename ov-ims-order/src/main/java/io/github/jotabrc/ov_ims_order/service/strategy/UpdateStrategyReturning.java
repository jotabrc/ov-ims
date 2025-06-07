package io.github.jotabrc.ov_ims_order.service.strategy;

import io.github.jotabrc.ov_ims_order.config.DomainConfig;
import io.github.jotabrc.ov_ims_order.controller.handler.ProductNotFoundException;
import io.github.jotabrc.ov_ims_order.dto.DetailDto;
import io.github.jotabrc.ov_ims_order.dto.DetailUpdateDto;
import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;
import io.github.jotabrc.ov_ims_order.model.Detail;
import io.github.jotabrc.ov_ims_order.model.Order;
import io.github.jotabrc.ov_ims_order.model.OrderStatus;
import io.github.jotabrc.ov_ims_order.service.DetailService;
import io.github.jotabrc.ov_ims_order.util.DtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UpdateStrategyReturning implements UpdateStrategy {

    private final DetailService detailService;
    private final DtoMapper dtoMapper;

    private final DomainConfig domainConfig;

    @Override
    public void apply(Order order, OrderUpdateTypeDto dto) {
        isReturningDateValid(order);
        List<DetailDto> newDetailsDtos = canReturnOrThrow(order, dto);
        List<Detail> newDetails = detailService.saveAll(newDetailsDtos);
        order.getDetails().addAll(newDetails);
    }

    private void isReturningDateValid(Order order) {
        boolean isReturningDateValid = order.getCreatedAt()
                .isBefore(order.getCreatedAt().plusDays(domainConfig.getMaxDays()));
        if (!isReturningDateValid) throw new IllegalStateException("Returning date exceeds required range");
    }

    private List<DetailDto> canReturnOrThrow(final Order order, final OrderUpdateTypeDto dto) {
        if (isReturnAvailable(order.getStatus())) return updateOrderOrThrow(order, dto);
        else throw new IllegalStateException("Order is ineligible for returns");
    }

    private boolean isReturnAvailable(final OrderStatus status) {
        return status.equals(OrderStatus.DELIVERED);
    }

    private List<DetailDto> updateOrderOrThrow(final Order order, final OrderUpdateTypeDto dto) {
        Map<String, Integer> detailQuantityMap = getAvailableQuantity(order);
        Map<Detail, DetailUpdateDto> detailMap = generateDetailMap(order, dto, detailQuantityMap);
        return getDetailDto(detailMap);
    }

    private Map<String, Integer> getAvailableQuantity(Order order) {
        return order.getDetails()
                .stream()
                .collect(Collectors.toMap(
                        Detail::getProductUuid,
                        Detail::getQuantity,
                        Integer::sum
                ));
    }

    private Map<Detail, DetailUpdateDto> generateDetailMap(
            final Order order,
            final OrderUpdateTypeDto dto,
            final Map<String, Integer> detailQuantityMap
    ) {
        return order
                .getDetails()
                .stream()
                .filter(detail -> detail.getQuantity() >= domainConfig.getMinQuantity())
                .collect(Collectors.toMap(
                        detail -> detail,
                        detail -> addProductToDetailMapOrThrow(dto, detail, detailQuantityMap.get(detail.getProductUuid()))
                ));
    }

    private DetailUpdateDto addProductToDetailMapOrThrow(
            final OrderUpdateTypeDto dto,
            final Detail detail,
            final int availableQuantity
    ) {
        AtomicBoolean hasQuantity = new AtomicBoolean(false);
        AtomicBoolean isReturningZero = new AtomicBoolean(false);
        Optional<DetailUpdateDto> detailUpdateDto = dto
                .getReturnItems()
                .stream()
                .filter(duDto -> {
                            hasQuantity.set(duDto.getQuantity() <= availableQuantity);
                            isReturningZero.set(duDto.getQuantity() == 0);
                            return hasQuantity.get() &&
                                    !isReturningZero.get() &&
                                    duDto.getProductUuid().equals(detail.getProductUuid());
                        }
                )
                .findFirst();
        if (isReturningZero.get())
            throw new IllegalStateException("Cannot return Zero items");
        else if (!hasQuantity.get())
            throw new IllegalStateException("Order available quantity is lower then returning amount");
        else if (detailUpdateDto.isEmpty())
            throw new ProductNotFoundException("Product not found");
        else
            return detailUpdateDto.get();
    }

    private List<DetailDto> getDetailDto(final Map<Detail, DetailUpdateDto> detailDetailUpdateDtoMap) {
        return detailDetailUpdateDtoMap
                .entrySet()
                .stream()
                .map(entry ->
                        dtoMapper.toReturnDto(entry.getKey(), entry.getValue())
                )
                .toList();
    }
}

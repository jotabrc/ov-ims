package io.github.jotabrc.ov_ims_product.util.mapper;

import io.github.jotabrc.ov_ims_product.dto.GetPage;
import io.github.jotabrc.ov_ims_product.dto.ProductDto;
import io.github.jotabrc.ov_ims_product.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor
public class MapperFromPageProductToGetPageProductDto implements MapperAbstractFactory<Page<Product>, GetPage<ProductDto>> {

    private final MapperAbstractFactory<Product, ProductDto> productMapperToDto;

    @Override
    public GetPage<ProductDto> mapFrom(Page<Product> page) {
        return GetPage
                .<ProductDto>builder()
                .content(
                        page.getContent()
                                .stream()
                                .map(productMapperToDto::mapFrom)
                                .toList()
                )
                .last(page.isLast())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .size(page.getSize())
                .number(page.getNumber())
                .numberOfElements(page.getNumberOfElements())
                .empty(page.isEmpty())
                .build();
    }
}

package io.github.jotabrc.ov_ims_product.util.mapper;

import io.github.jotabrc.ov_ims_product.dto.ProductDto;
import io.github.jotabrc.ov_ims_product.model.Product;

import java.util.UUID;

public class MapperFromProductDtoToProduct implements MapperAbstractFactory<ProductDto, Product> {
    @Override
    public Product mapFrom(ProductDto productDto) {
        return Product
                .builder()
                .uuid(UUID.randomUUID().toString())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .isActive(true)
                .build();
    }
}

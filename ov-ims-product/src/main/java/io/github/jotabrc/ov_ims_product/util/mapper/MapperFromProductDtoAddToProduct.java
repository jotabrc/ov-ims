package io.github.jotabrc.ov_ims_product.util.mapper;

import io.github.jotabrc.ov_ims_product.dto.ProductDtoAdd;
import io.github.jotabrc.ov_ims_product.model.Product;

import java.util.UUID;

public class MapperFromProductDtoAddToProduct implements MapperAbstractFactory<ProductDtoAdd, Product> {
    @Override
    public Product mapFrom(ProductDtoAdd productDtoAdd) {
        return Product
                .builder()
                .uuid(UUID.randomUUID().toString())
                .name(productDtoAdd.getName())
                .description(productDtoAdd.getDescription())
                .isActive(true)
                .build();
    }
}

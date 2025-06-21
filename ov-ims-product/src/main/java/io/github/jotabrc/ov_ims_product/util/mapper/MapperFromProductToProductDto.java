package io.github.jotabrc.ov_ims_product.util.mapper;

import io.github.jotabrc.ov_ims_product.dto.CategoryDto;
import io.github.jotabrc.ov_ims_product.dto.ProductDto;
import io.github.jotabrc.ov_ims_product.model.Category;
import io.github.jotabrc.ov_ims_product.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component @AllArgsConstructor
public class MapperFromProductToProductDto implements MapperAbstractFactory<Product, ProductDto> {

    private final MapperAbstractFactory<Category, CategoryDto> categoryMapper;

    @Override
    public ProductDto mapFrom(Product product) {
        return ProductDto
                .builder()
                .uuid(product.getUuid())
                .name(product.getName())
                .description(product.getDescription())
                .categories(product.getCategories()
                        .stream()
                        .map(categoryMapper::mapFrom)
                        .collect(Collectors.toUnmodifiableSet()))
                .build();
    }
}

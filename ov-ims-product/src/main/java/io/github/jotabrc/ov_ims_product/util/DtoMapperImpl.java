package io.github.jotabrc.ov_ims_product.util;

import io.github.jotabrc.ov_ims_product.dto.CategoryDto;
import io.github.jotabrc.ov_ims_product.dto.ProductDto;
import io.github.jotabrc.ov_ims_product.model.Category;
import io.github.jotabrc.ov_ims_product.model.Product;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DtoMapperImpl implements DtoMapper {

    @Override
    public ProductDto toDto(Product product) {
        return ProductDto
                .builder()
                .uuid(product.getUuid())
                .name(product.getName())
                .description(product.getDescription())
                .categories(product.getCategories()
                        .stream()
                        .map(this::toDto)
                        .collect(Collectors.toUnmodifiableSet()))
                .build();
    }

    @Override
    public CategoryDto toDto(Category category) {
        return CategoryDto
                .builder()
                .uuid(category.getUuid())
                .name(category.getName())
                .build();
    }
}

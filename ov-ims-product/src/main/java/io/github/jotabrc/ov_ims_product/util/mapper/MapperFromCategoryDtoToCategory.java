package io.github.jotabrc.ov_ims_product.util.mapper;

import io.github.jotabrc.ov_ims_product.dto.CategoryDto;
import io.github.jotabrc.ov_ims_product.model.Category;

import java.util.UUID;

public class MapperFromCategoryDtoToCategory implements MapperAbstractFactory<CategoryDto, Category> {
    @Override
    public Category mapFrom(CategoryDto categoryDto) {
        return Category
                .builder()
                .uuid(UUID.randomUUID().toString())
                .name(categoryDto.getName())
                .isActive(true)
                .build();
    }
}

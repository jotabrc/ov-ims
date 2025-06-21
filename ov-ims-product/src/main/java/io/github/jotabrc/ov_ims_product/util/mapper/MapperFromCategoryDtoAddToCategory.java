package io.github.jotabrc.ov_ims_product.util.mapper;

import io.github.jotabrc.ov_ims_product.dto.CategoryDtoAdd;
import io.github.jotabrc.ov_ims_product.model.Category;

import java.util.UUID;

public class MapperFromCategoryDtoAddToCategory implements MapperAbstractFactory<CategoryDtoAdd, Category> {
    @Override
    public Category mapFrom(CategoryDtoAdd categoryDtoAdd) {
        return Category
                .builder()
                .uuid(UUID.randomUUID().toString())
                .name(categoryDtoAdd.getName())
                .isActive(true)
                .build();
    }
}

package io.github.jotabrc.ov_ims_product.util.mapper;

import io.github.jotabrc.ov_ims_product.dto.CategoryDto;
import io.github.jotabrc.ov_ims_product.model.Category;
import org.springframework.stereotype.Component;

@Component
public class MapperFromCategoryToCategoryDto implements MapperAbstractFactory<Category, CategoryDto> {

    @Override
    public CategoryDto mapFrom(Category category) {
        return CategoryDto
                .builder()
                .uuid(category.getUuid())
                .name(category.getName())
                .build();
    }
}

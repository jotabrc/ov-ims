package io.github.jotabrc.ov_ims_product.util.mapper;

import io.github.jotabrc.ov_ims_product.dto.CategoryDto;
import io.github.jotabrc.ov_ims_product.dto.CategoryDtoAdd;
import org.springframework.stereotype.Component;

@Component
public class MapperFromCategoryDtoAddToCategoryDto implements MapperAbstractFactory<CategoryDtoAdd, CategoryDto> {

    @Override
    public CategoryDto mapFrom(CategoryDtoAdd categoryDtoAdd) {
        return CategoryDto
                .builder()
                .name(categoryDtoAdd.getName())
                .build();
    }
}

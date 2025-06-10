package io.github.jotabrc.ov_ims_product.util;

import io.github.jotabrc.ov_ims_product.dto.CategoryDto;
import io.github.jotabrc.ov_ims_product.dto.CategoryDtoAdd;
import io.github.jotabrc.ov_ims_product.dto.ProductDto;
import io.github.jotabrc.ov_ims_product.dto.ProductDtoAdd;
import io.github.jotabrc.ov_ims_product.model.Category;
import io.github.jotabrc.ov_ims_product.model.Product;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EntityCreatorMapperImpl implements EntityCreatorMapper {

    @Override
    public Product toEntity(final ProductDto dto) {
        return Product
                .builder()
                .uuid(UUID.randomUUID().toString())
                .name(dto.getName())
                .description(dto.getDescription())
                .isActive(true)
                .build();
    }

    @Override
    public Category toEntity(final CategoryDto dto) {
        return Category
                .builder()
                .uuid(UUID.randomUUID().toString())
                .name(dto.getName())
                .isActive(true)
                .build();
    }

    @Override
    public Product toEntity(final ProductDtoAdd dto) {
        return Product
                .builder()
                .uuid(UUID.randomUUID().toString())
                .name(dto.getName())
                .description(dto.getDescription())
                .isActive(true)
                .build();
    }

    @Override
    public Category toEntity(final CategoryDtoAdd dto) {
        return Category
                .builder()
                .uuid(UUID.randomUUID().toString())
                .name(dto.getName())
                .isActive(true)
                .build();
    }
}

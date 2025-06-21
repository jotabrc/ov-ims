package io.github.jotabrc.ov_ims_product.util;

import io.github.jotabrc.ov_ims_product.dto.CategoryDto;
import io.github.jotabrc.ov_ims_product.dto.CategoryDtoAdd;
import io.github.jotabrc.ov_ims_product.dto.ProductDto;
import io.github.jotabrc.ov_ims_product.dto.ProductDtoAdd;
import io.github.jotabrc.ov_ims_product.model.Category;
import io.github.jotabrc.ov_ims_product.model.Product;

@Deprecated(forRemoval = true)
public interface EntityCreatorMapper {

    Product toEntity(ProductDto dto);
    Category toEntity(CategoryDto dto);
    Product toEntity(ProductDtoAdd dto);
    Category toEntity(CategoryDtoAdd dto);
}

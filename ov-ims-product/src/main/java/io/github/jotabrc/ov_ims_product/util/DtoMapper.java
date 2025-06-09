package io.github.jotabrc.ov_ims_product.util;

import io.github.jotabrc.ov_ims_product.dto.CategoryDto;
import io.github.jotabrc.ov_ims_product.dto.GetPage;
import io.github.jotabrc.ov_ims_product.dto.ProductDto;
import io.github.jotabrc.ov_ims_product.model.Category;
import io.github.jotabrc.ov_ims_product.model.Product;
import org.springframework.data.domain.Page;

public interface DtoMapper {

    ProductDto toDto(Product product);
    CategoryDto toDto(Category category);
    GetPage<ProductDto> toDto(Page<Product> page);
}

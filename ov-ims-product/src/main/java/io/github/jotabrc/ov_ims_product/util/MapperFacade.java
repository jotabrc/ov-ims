package io.github.jotabrc.ov_ims_product.util;

import io.github.jotabrc.ov_ims_product.dto.*;
import io.github.jotabrc.ov_ims_product.model.Category;
import io.github.jotabrc.ov_ims_product.model.Product;
import org.springframework.data.domain.Page;

public interface MapperFacade {

    ProductDto productToProductDto(Product product);
    CategoryDto categoryToCategoryDto(Category category);
    Category categoryDtoAddToCategory(CategoryDtoAdd categoryDtoAdd);
    CategoryDto categoryDtoAddToCategoryDto(CategoryDtoAdd categoryDtoAdd);
    Category categoryDtoToCategory(CategoryDto categoryDto);
    GetPage<ProductDto> pageProductToGetPageProductDto(Page<Product> productPage);
    Product productDtoAddToProduct(ProductDtoAdd productDtoAdd);
    Product productDtoToProduct(ProductDto productDto);
}

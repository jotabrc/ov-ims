package io.github.jotabrc.ov_ims_product.util;

import io.github.jotabrc.ov_ims_product.dto.*;
import io.github.jotabrc.ov_ims_product.model.Category;
import io.github.jotabrc.ov_ims_product.model.Product;
import io.github.jotabrc.ov_ims_product.util.mapper.MapperAbstractFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor
public class MapperFacadeImpl implements MapperFacade {
    private final MapperAbstractFactory<Product, ProductDto> productMapperToProductDto;

    private final MapperAbstractFactory<Category, CategoryDto> categoryMapperToCategoryDto;
    private final MapperAbstractFactory<CategoryDtoAdd, Category> categoryDtoAddMapperToCategory;
    private final MapperAbstractFactory<CategoryDtoAdd, CategoryDto> categoryDtoAddMapperToCategoryDto;
    private final MapperAbstractFactory<CategoryDto, Category> categoryDtoMapperToCategory;
    private final MapperAbstractFactory<Page<Product>, GetPage<ProductDto>> pageProductMapperToGetPageProductDto;
    private final MapperAbstractFactory<ProductDtoAdd, Product> productDtoAddMapperToProduct;
    private final MapperAbstractFactory<ProductDto, Product> productDtoMapperToProduct;


    @Override
    public ProductDto productToProductDto(Product product) {
        return productMapperToProductDto.mapFrom(product);
    }

    @Override
    public CategoryDto categoryToCategoryDto(Category category) {
        return categoryMapperToCategoryDto.mapFrom(category);
    }

    @Override
    public Category categoryDtoAddToCategory(CategoryDtoAdd categoryDtoAdd) {
        return categoryDtoAddMapperToCategory.mapFrom(categoryDtoAdd);
    }

    @Override
    public CategoryDto categoryDtoAddToCategoryDto(CategoryDtoAdd categoryDtoAdd) {
        return categoryDtoAddMapperToCategoryDto.mapFrom(categoryDtoAdd);
    }

    @Override
    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        return categoryDtoMapperToCategory.mapFrom(categoryDto);
    }

    @Override
    public GetPage<ProductDto> pageProductToGetPageProductDto(Page<Product> productPage) {
        return pageProductMapperToGetPageProductDto.mapFrom(productPage);
    }

    @Override
    public Product productDtoAddToProduct(ProductDtoAdd productDtoAdd) {
        return productDtoAddMapperToProduct.mapFrom(productDtoAdd);
    }

    @Override
    public Product productDtoToProduct(ProductDto productDto) {
        return productDtoMapperToProduct.mapFrom(productDto);
    }
}

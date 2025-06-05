package io.github.jotabrc.ov_ims_product.service;

import io.github.jotabrc.ov_ims_product.dto.CategoryDto;
import io.github.jotabrc.ov_ims_product.model.Category;

import java.util.Set;

public interface CategoryService {

    void saveAll(Set<Category> categories);
    Set<Category> resolveCategories(Set<CategoryDto> dtos);
}

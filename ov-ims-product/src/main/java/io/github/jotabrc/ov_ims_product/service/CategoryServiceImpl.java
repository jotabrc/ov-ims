package io.github.jotabrc.ov_ims_product.service;

import io.github.jotabrc.ov_ims_product.dto.CategoryDto;
import io.github.jotabrc.ov_ims_product.model.Category;
import io.github.jotabrc.ov_ims_product.repository.CategoryRepository;
import io.github.jotabrc.ov_ims_product.util.EntityCreatorMapperImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EntityCreatorMapperImpl entityMapperImpl;

    @Transactional
    @Override
    public void saveAll(Set<Category> categories) {
        categoryRepository.saveAll(categories);
    }

    @Transactional
    @Override
    public Set<Category> resolveCategories(final Set<CategoryDto> dtos) {
        Set<Category> newCategories = new HashSet<>();
        Set<Category> categories = dtos
                .stream()
                .map(c -> findOrCreate(c, newCategories))
                .collect(Collectors.toSet());
        saveAll(newCategories);
        return categories;
    }

    private Category findOrCreate(final CategoryDto dto, Set<Category> newCategories) {
        return
                categoryRepository.findByUuid(dto.getUuid())
                        .orElseGet(() -> categoryRepository.findByName(dto.getName()).orElseGet(() -> {
                                    Category category = entityMapperImpl.toEntity(dto);
                                    newCategories.add(category);
                                    return category;
                                })
                        );
    }
}

package io.github.jotabrc.ov_ims_product.service;

import io.github.jotabrc.ov_ims_product.dto.CategoryDto;
import io.github.jotabrc.ov_ims_product.dto.ProductDto;
import io.github.jotabrc.ov_ims_product.model.Category;
import io.github.jotabrc.ov_ims_product.model.Product;
import io.github.jotabrc.ov_ims_product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryService categoryService;

    @Captor
    private ArgumentCaptor<Set<CategoryDto>> captor;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testValueFromRepositorySave() {
        Set<CategoryDto> categoriesDto = Set.of(new CategoryDto("category-uuid", "products"));
        Set<Category> categories = new HashSet<>();
        categories.add(new Category(1, "category-uuid", "products", LocalDateTime.now(ZoneOffset.UTC), LocalDateTime.now(ZoneOffset.UTC), true, 0));
        ProductDto dto = new ProductDto("uuid", "name", "description", categoriesDto);
        Mockito.when(productRepository.findByUuid(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(new Product(1, "uuid", "name", "description", categories, LocalDateTime.now(ZoneOffset.UTC), LocalDateTime.now(ZoneOffset.UTC), true, 0)));
        Mockito.when(categoryService.resolveCategories(categoriesDto))
                .thenReturn(categories);
        productService.update(dto);
        Mockito.verify(categoryService).resolveCategories(captor.capture());
        assert !captor.getValue().isEmpty();
    }

}

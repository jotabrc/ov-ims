package io.github.jotabrc.ov_ims_product.service;

import io.github.jotabrc.ov_ims_product.dto.PageFilter;
import io.github.jotabrc.ov_ims_product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    String save(ProductDto dto);
    void update(ProductDto dto);
    void deactivate(String uuid);
    Page<ProductDto> get(PageFilter pageFilter, Pageable pageable);
}

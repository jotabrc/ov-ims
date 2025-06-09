package io.github.jotabrc.ov_ims_product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_ims_product.dto.GetPage;
import io.github.jotabrc.ov_ims_product.dto.PageFilter;
import io.github.jotabrc.ov_ims_product.dto.ProductDto;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    String save(ProductDto dto) throws JsonProcessingException;
    void update(ProductDto dto);
    void deactivate(String uuid);
    GetPage<ProductDto> get(PageFilter pageFilter, Pageable pageable);
}

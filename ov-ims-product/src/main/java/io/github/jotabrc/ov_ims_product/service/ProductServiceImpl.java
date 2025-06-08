package io.github.jotabrc.ov_ims_product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_ims_product.controller.handler.ProductNotFoundException;
import io.github.jotabrc.ov_ims_product.dto.PageFilter;
import io.github.jotabrc.ov_ims_product.dto.ProductDto;
import io.github.jotabrc.ov_ims_product.kafka.KafkaProducer;
import io.github.jotabrc.ov_ims_product.model.Category;
import io.github.jotabrc.ov_ims_product.model.Product;
import io.github.jotabrc.ov_ims_product.repository.ProductRepository;
import io.github.jotabrc.ov_ims_product.util.DtoMapper;
import io.github.jotabrc.ov_ims_product.util.EntityCreatorMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    private final KafkaProducer kafkaProducer;
    private final EntityCreatorMapper entityMapper;
    private final DtoMapper dtoMapper;

    @Transactional
    @Override
    public String save(final ProductDto dto) throws JsonProcessingException {
        Product product = entityMapper.toEntity(dto);
        Set<Category> categories = categoryService.resolveCategories(dto.getCategories());
        product.setCategories(categories);
        String uuid = productRepository.save(product).getUuid();
        sendProductNewTopic(uuid);
        return uuid;
    }

    @Transactional
    @Override
    public void update(final ProductDto dto) {
        Product product = getOrElseThrow(dto.getUuid());
        Set<Category> categories = categoryService.resolveCategories(dto.getCategories());
        product.getCategories().addAll(categories);
        updateProduct(product, dto);
        productRepository.save(product);
    }

    @Override
    public void deactivate(final String uuid) {
        Product product = getOrElseThrow(uuid);
        product.setActive(false);
        productRepository.save(product);
    }

    @Override
    public Page<ProductDto> get(final PageFilter pageFilter, final Pageable pageable) {
        return productRepository
                .getWith(pageFilter.getUuid(), pageFilter.getCategory(), pageable)
                .map(dtoMapper::toDto);
    }

    private Product getOrElseThrow(final String uuid) {
        return productRepository.findByUuid(uuid)
                .orElseThrow(() -> new ProductNotFoundException("Product with UUID %s not found".formatted(uuid)));
    }

    private void updateProduct(final Product product, final ProductDto dto) {
        product
                .setName(dto.getName())
                .setDescription(dto.getDescription());
    }

    @Async
    private void sendProductNewTopic(String uuid) throws JsonProcessingException {
        kafkaProducer.produce(
                kafkaProducer.build(uuid),
                "PRODUCT_NEW"
        );
    }
}

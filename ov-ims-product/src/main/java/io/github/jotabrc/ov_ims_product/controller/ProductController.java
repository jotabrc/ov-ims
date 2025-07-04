package io.github.jotabrc.ov_ims_product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_ims_product.dto.GetPage;
import io.github.jotabrc.ov_ims_product.dto.PageFilter;
import io.github.jotabrc.ov_ims_product.dto.ProductDto;
import io.github.jotabrc.ov_ims_product.dto.ProductDtoAdd;
import io.github.jotabrc.ov_ims_product.service.ProductService;
import io.github.jotabrc.ov_ims_product.validation.StringType;
import io.github.jotabrc.ov_ims_product.validation.ValidString;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Validated @AllArgsConstructor @RestController
@RequestMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Product Management", description = "Operations related to products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody final ProductDtoAdd dto) throws JsonProcessingException {
        final String uuid = productService.save(dto);
        URI location = ServletUriComponentsBuilder
                .fromPath("/product/{uuid}")
                .buildAndExpand(uuid)
                .toUri();
        return ResponseEntity
                .created(location)
                .build();
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody final ProductDto dto) {
        productService.update(dto);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<String> deactivate(
            @ValidString(message = "Invalid UUID format", type = StringType.UUID)
            @PathVariable("uuid") final String uuid
    ) {
        productService.deactivate(uuid);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping
    public ResponseEntity<GetPage<ProductDto>> get(
            @ValidString(message = "Invalid UUID format", type = StringType.UUID, isRequired = false)
            @RequestParam(required = false) final String uuid,
            @ValidString(message = "Invalid Category name format", type = StringType.NAME, isRequired = false)
            @RequestParam(required = false) final String category,
            @NotNull final Pageable pageable
            ) {
        PageFilter pageFilter = PageFilter
                .builder()
                .uuid(uuid)
                .category(category)
                .build();
        GetPage<ProductDto> page = productService.get(pageFilter, pageable);
        return ResponseEntity
                .ok(page);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<String> options() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .allow(HttpMethod.POST, HttpMethod.PUT, HttpMethod.GET)
                .build();
    }

    @RequestMapping(path = "/{uuid}", method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsPatch() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .allow(HttpMethod.PATCH)
                .build();
    }
}

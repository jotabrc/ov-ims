package io.github.jotabrc.ov_ims_order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.jotabrc.ov_ims_order.dto.OrderDto;
import io.github.jotabrc.ov_ims_order.dto.OrderDtoAdd;
import io.github.jotabrc.ov_ims_order.dto.OrderUpdateTypeDto;
import io.github.jotabrc.ov_ims_order.dto.PageFilter;
import io.github.jotabrc.ov_ims_order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody final OrderDtoAdd dto) throws JsonProcessingException {
        String uuid = orderService.save(dto);
        URI location = ServletUriComponentsBuilder
                .fromPath("/order/{uuid}")
                .buildAndExpand(uuid)
                .toUri();
        return ResponseEntity
                .created(location)
                .build();
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody OrderUpdateTypeDto dto) throws JsonProcessingException {
        orderService.update(dto);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping
    public ResponseEntity<Page<OrderDto>> get(
            @RequestParam(required = false) final String uuid,
            @RequestParam(required = false) final String placedBy,
            @NotNull final Pageable pageable
            ) {
        PageFilter pageFilter = PageFilter
                .builder()
                .uuid(uuid)
                .placedBy(placedBy)
                .build();
        Page<OrderDto> page = orderService.get(pageFilter, pageable);
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
}

package io.github.jotabrc.ov_ims_product.controller.handler;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}

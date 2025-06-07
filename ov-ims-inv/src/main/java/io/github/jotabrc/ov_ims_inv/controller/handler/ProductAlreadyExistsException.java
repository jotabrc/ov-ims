package io.github.jotabrc.ov_ims_inv.controller.handler;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}

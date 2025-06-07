package io.github.jotabrc.ov_ims_inv.controller.handler;

public class UnavailableStockException extends RuntimeException {
    public UnavailableStockException(String message) {
        super(message);
    }
}

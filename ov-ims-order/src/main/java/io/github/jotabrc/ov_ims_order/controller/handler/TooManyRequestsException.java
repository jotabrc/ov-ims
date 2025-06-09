package io.github.jotabrc.ov_ims_order.controller.handler;

public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException(String message) {
        super(message);
    }
}

package io.github.jotabrc.ov_ims_order.controller.handler;

public class OrderNotFoundException extends RuntimeException {
  public OrderNotFoundException(String message) {
    super(message);
  }
}

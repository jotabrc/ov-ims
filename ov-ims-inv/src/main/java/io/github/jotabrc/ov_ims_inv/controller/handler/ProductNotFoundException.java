package io.github.jotabrc.ov_ims_inv.controller.handler;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(String message) {
    super(message);
  }
}

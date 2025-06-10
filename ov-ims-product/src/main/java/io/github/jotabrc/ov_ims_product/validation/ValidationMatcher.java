package io.github.jotabrc.ov_ims_product.validation;

import jakarta.validation.constraints.NotNull;

public interface ValidationMatcher {

    <T> boolean isNull(T t);
    boolean isNotBlank(String string);
    boolean isNotNullNorBlank(String string);
    boolean match(@NotNull String string, @NotNull String regex);
}

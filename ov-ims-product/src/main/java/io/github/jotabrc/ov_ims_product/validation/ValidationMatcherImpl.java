package io.github.jotabrc.ov_ims_product.validation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.regex.Pattern;

@Service @AllArgsConstructor @Validated
public class ValidationMatcherImpl implements ValidationMatcher {

    @Override
    public <T> boolean isNull(final T t) {
        return t == null;
    }

    @Override
    public boolean isNotBlank(final String string) {
        return !string.isBlank();
    }

    @Override
    public boolean isNotNullNorBlank(final String string) {
        return !isNull(string) && !isNotBlank(string);
    }

    @Override
    public boolean match(@NotNull final String string, @NotNull final String regex) {
        return Pattern
                .compile(regex)
                .matcher(string)
                .matches();
    }
}

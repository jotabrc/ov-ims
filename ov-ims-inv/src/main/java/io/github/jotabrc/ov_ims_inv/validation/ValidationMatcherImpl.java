package io.github.jotabrc.ov_ims_inv.validation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.regex.Pattern;

@Component
@AllArgsConstructor @Validated
public class ValidationMatcherImpl implements ValidationMatcher {

    @Override
    public <T> boolean isNotNull(final T t) {
        return t != null;
    }

    @Override
    public boolean isNotBlank(final String string) {
        return !string.isBlank();
    }

    @Override
    public boolean isNotNullNorBlank(final String string) {
        return isNotNull(string) && isNotBlank(string);
    }

    @Override
    public boolean match(@NotNull final String string, @NotNull final String regex) {
        return Pattern
                .compile(regex)
                .matcher(string)
                .matches();
    }
}

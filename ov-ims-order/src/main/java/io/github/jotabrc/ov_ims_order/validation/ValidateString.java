package io.github.jotabrc.ov_ims_order.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class ValidateString implements ConstraintValidator<ValidString, String> {

    private final ValidationMatcher validationMatcher;
    private final ValidationConfig config;
    private String pattern;
    private boolean isRequired;
    private String message;

    @Override
    public boolean isValid(String string, ConstraintValidatorContext context) {
        boolean isValid;

        // A required field MUST NOT be null or blank and match the pattern
        // A !required field MUST match the pattern only if it is not blank or null;
        if (isRequired) isValid = validationMatcher.isNotNullNorBlank(string) && validationMatcher.match(string, pattern);
        else isValid = !validationMatcher.isNotNullNorBlank(string) || validationMatcher.match(string, pattern);

        return buildContext(context, isValid);
    }

    @Override
    public void initialize(ValidString annotation) {
        selectPattern(annotation);
        this.isRequired = annotation.isRequired();
        this.message = annotation.message();
        ConstraintValidator.super.initialize(annotation);
    }

    private void selectPattern(ValidString annotation) {
        switch (annotation.type()) {
            case NAME -> this.pattern = config.getName();
            case UUID -> this.pattern = config.getUuid();
            default -> this.pattern = config.getString();
        }
    }

    private boolean buildContext(ConstraintValidatorContext context, boolean isValid) {
        if (isValid) return true;
        else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;
        }
    }
}

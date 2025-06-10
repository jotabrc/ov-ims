package io.github.jotabrc.ov_ims_inv.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidateString.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidString {
    String message() default "Invalid String format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    StringType type() default StringType.STRING;
    boolean isRequired() default true;
}

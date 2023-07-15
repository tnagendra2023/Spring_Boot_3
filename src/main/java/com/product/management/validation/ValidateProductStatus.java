package com.product.management.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ProductStatusValidator.class)
public @interface ValidateProductStatus {
    String message() default "Invalid product status: It should be either active Or inactive";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

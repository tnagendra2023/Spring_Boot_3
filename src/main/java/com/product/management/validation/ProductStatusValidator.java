package com.product.management.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class ProductStatusValidator implements ConstraintValidator<ValidateProductStatus,String> {

    @Override
    public boolean isValid(String status, ConstraintValidatorContext constraintValidatorContext) {
        List<String> employeeTypes = Arrays.asList("active", "inactive");
        return employeeTypes.contains(status);
    }
}

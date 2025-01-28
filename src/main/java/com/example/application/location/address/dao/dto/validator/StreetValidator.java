package com.example.application.location.address.dao.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StreetValidator implements ConstraintValidator<StreetConstraint, String> {
    @Override
    public void initialize(StreetConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String street, ConstraintValidatorContext constraintValidatorContext) {
        return street != null && street.matches("[A-Za-z0-9]")
                && (street.length() > 0) && (street.length() < 20);
    }
}

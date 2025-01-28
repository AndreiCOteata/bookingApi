package com.example.application.location.city.dao.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostalCodeValidator implements ConstraintValidator<PostalCodeConstraint, String> {

    @Override
    public void initialize(PostalCodeConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String postalCode, ConstraintValidatorContext constraintValidatorContext) {
        return postalCode != null && postalCode.matches("[0-9]")
                && (postalCode.length() > 0) && (postalCode.length() < 8);
    }
}

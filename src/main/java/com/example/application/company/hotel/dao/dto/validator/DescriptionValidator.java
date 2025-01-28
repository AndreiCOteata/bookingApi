package com.example.application.company.hotel.dao.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DescriptionValidator implements ConstraintValidator<DescriptionConstraint, String> {
    @Override
    public void initialize(DescriptionConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String description, ConstraintValidatorContext constraintValidatorContext) {
        return description != null && description.matches("[a-zA-Z0-9]")
                && (description.length() <= 160);
    }
}

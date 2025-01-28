package com.example.application.location.address.dao.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<NumberConstraint, String> {
    @Override
    public void initialize(NumberConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {
        return number != null && number.matches("[0-9]")
                && (number.length() > 0) && (number.length() < 4);
    }
}

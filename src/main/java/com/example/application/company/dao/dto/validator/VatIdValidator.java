package com.example.application.company.dao.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VatIdValidator implements ConstraintValidator<VatIdConstraint, String> {

    @Override
    public void initialize(VatIdConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String vatId, ConstraintValidatorContext constraintValidatorContext) {
        return vatId != null && vatId.matches("([0-9])+\\s+([A-Z]?)")
                && (vatId.length() > 6) && (vatId.length() < 13);
    }


}

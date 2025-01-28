package com.example.application.account.dao.dto.validator;

import com.example.application.config.security.model.ResetPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, ResetPassword> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final ResetPassword obj, final ConstraintValidatorContext context) {
        return obj.getPassword().equals(obj.getMatchingPassword());
    }
}

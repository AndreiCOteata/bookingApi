package com.example.application.account.dao.dto.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        return (validateEmail(username));
    }

    public boolean validateEmail(String email) {
        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }
}

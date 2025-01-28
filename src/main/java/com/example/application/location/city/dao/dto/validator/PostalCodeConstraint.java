package com.example.application.location.city.dao.dto.validator;

import com.example.application.location.address.dao.dto.validator.NumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PostalCodeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PostalCodeConstraint {
    String message() default "Invalid postal code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

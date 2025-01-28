package com.example.application.company.hotel.dao.dto.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DescriptionValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DescriptionConstraint {
    String message() default "Description exceeds limit of 160 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.example.application.company.hotel.dao.dto.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameConstraint {

    String message() default "Name exceeds limit of 20 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

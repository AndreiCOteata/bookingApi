package com.example.application.location.address.dao.dto.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StreetValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StreetConstraint {
    String message() default "Invalid street name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

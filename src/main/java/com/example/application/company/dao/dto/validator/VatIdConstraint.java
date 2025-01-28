package com.example.application.company.dao.dto.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VatIdValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VatIdConstraint {

    String message() default "Invalid Vat Id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

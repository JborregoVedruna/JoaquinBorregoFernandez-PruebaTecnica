package com.caixabank.loansmanager.domain.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.caixabank.loansmanager.domain.validations.validators.DNIValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = DNIValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DNI {
    String message() default "Invalid DNI";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

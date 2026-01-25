package com.caixabank.loansmanager.domain.validations;

import com.caixabank.loansmanager.domain.validations.validators.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Anotación personalizada para validar la complejidad de una contraseña. */
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
  /** Mensaje de error detallando los requisitos de complejidad. */
  String message() default
      "The password does not meet the complexity requirements: minimum 8 characters, one uppercase letter, one lowercase letter, one digit and one special character (@#$%^&+=!)";

  /** Grupos de validación. */
  Class<?>[] groups() default {};

  /** Carga útil (payload) de la validación. */
  Class<? extends Payload>[] payload() default {};
}

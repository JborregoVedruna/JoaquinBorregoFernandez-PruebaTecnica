package com.caixabank.loansmanager.domain.validations;

import com.caixabank.loansmanager.domain.validations.validators.DNIValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Anotación personalizada para validar el formato de un DNI español. */
@Constraint(validatedBy = DNIValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DNI {
  /** Mensaje de error por defecto. */
  String message() default "Invalid ID format";

  /** Grupos de validación. */
  Class<?>[] groups() default {};

  /** Carga útil (payload) de la validación. */
  Class<? extends Payload>[] payload() default {};
}

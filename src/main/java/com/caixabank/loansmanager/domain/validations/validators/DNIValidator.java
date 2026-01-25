package com.caixabank.loansmanager.domain.validations.validators;

import com.caixabank.loansmanager.domain.validations.DNI;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementación del validador para la anotación {@link DNI}.
 *
 * <p>Verifica que el formato del DNI español sea correcto (8 dígitos y letra de control válida).
 */
@Slf4j
public class DNIValidator implements ConstraintValidator<DNI, String> {
  /** Cadena de letras para el cálculo del DNI español. */
  private static final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";

  /**
   * Inicializa el validador.
   *
   * @param constraintAnnotation Instancia de la anotación.
   */
  @Override
  public void initialize(DNI constraintAnnotation) {
    // No initialization needed for this validator
  }

  /**
   * Verifica si el DNI proporcionado es válido.
   *
   * @param value El valor del DNI a validar.
   * @param context Contexto del validador.
   * @return true si es válido, false en caso contrario.
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    log.info("Validating DNI: {}", value);
    if (value == null || value.length() != 9) {
      log.warn("Invalid DNI length or null: {}", value);
      return false;
    }
    try {
      int number = Integer.parseInt(value.substring(0, 8));
      char character = value.charAt(8);

      int rm = number % 23;
      char dniChar = LETRAS.charAt(rm);
      log.info("Number: {}", number, "must have letter: {}", dniChar);
      return character == dniChar;
    } catch (NumberFormatException e) {
      log.warn("Invalid DNI format: {}", value);
      return false;
    }
  }
}

package com.caixabank.loansmanager.domain.validations.validators;

import com.caixabank.loansmanager.domain.validations.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Implementación del validador para la anotación {@link Password}.
 *
 * <p>Verifica que la contraseña cumpla con los criterios mínimos de seguridad mediante una
 * expresión regular.
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

  /** Patrón regex para validar la complejidad de la contraseña. */
  private static final String PASSWORD_PATTERN =
      "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

  /**
   * Verifica si la contraseña cumple con los requisitos de complejidad.
   *
   * @param value La contraseña a validar.
   * @param context Contexto del validador.
   * @return true si cumple los criterios, false en caso contrario.
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }
    return value.matches(PASSWORD_PATTERN);
  }
}

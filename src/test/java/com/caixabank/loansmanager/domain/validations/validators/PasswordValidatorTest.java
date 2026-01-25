package com.caixabank.loansmanager.domain.validations.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Clase de prueba unitaria para {@link PasswordValidator}.
 *
 * <p>Verifica el cumplimiento de las políticas de seguridad de contraseñas (longitud, caracteres
 * especiales, etc.).
 */
class PasswordValidatorTest {

  /** Validador bajo prueba. */
  private PasswordValidator validator;

  @BeforeEach
  void setUp() {
    validator = new PasswordValidator();
  }

  /**
   * Verifica que las contraseñas que cumplen todos los criterios de complejidad sean aceptadas.
   *
   * @param password Contraseña de ejemplo válida.
   */
  @ParameterizedTest
  @ValueSource(strings = {"Password123!", "Abcd@1234", "Valid#Pass1", "S0me$pecial"})
  void isValid_ShouldReturnTrue_ForComplexPasswords(String password) {
    assertTrue(validator.isValid(password, null));
  }

  /**
   * Verifica que las contraseñas que incumplen algún criterio de seguridad sean rechazadas.
   *
   * @param password Contraseña de ejemplo inválida.
   */
  @ParameterizedTest
  @ValueSource(
      strings = {
        "short1!", // too short
        "Password123", // no special char
        "password123!", // no uppercase
        "PASSWORD123!", // no lowercase
        "Password!", // no digit
        "Pass word123!", // contains whitespace
        "" // empty
      })
  void isValid_ShouldReturnFalse_ForInvalidPasswords(String password) {
    assertFalse(validator.isValid(password, null));
  }

  /** Verifica el comportamiento del validador ante una entrada nula. */
  @Test
  void isValid_ShouldReturnFalse_ForNull() {
    assertFalse(validator.isValid(null, null));
  }
}

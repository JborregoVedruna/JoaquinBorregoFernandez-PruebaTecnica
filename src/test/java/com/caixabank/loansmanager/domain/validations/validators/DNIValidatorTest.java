package com.caixabank.loansmanager.domain.validations.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de prueba unitaria para {@link DNIValidator}.
 *
 * <p>Verifica la lógica de validación de DNIs españoles, incluyendo formato y letra de control.
 */
class DNIValidatorTest {

  /** Validador bajo prueba. */
  private DNIValidator dniValidator;

  @BeforeEach
  void setUp() {
    dniValidator = new DNIValidator();
  }

  /** Verifica que un DNI válido sea aceptado correctamente. */
  @Test
  void isValid_ShouldReturnTrue_WhenDNIIsValid() {
    // 12345678Z -> 12345678 % 23 = 14. Letters: TRWAGMYFPDXBNJZSQVHLCKE. Index 14
    // is Z.
    assertTrue(dniValidator.isValid("12345678Z", null));
  }

  /** Verifica que se rechace un DNI si la letra de control es incorrecta. */
  @Test
  void isValid_ShouldReturnFalse_WhenDNILetterIsIncorrect() {
    // 12345678Z is valid, so 12345678A should be invalid
    assertFalse(dniValidator.isValid("12345678A", null));
  }

  /** Verifica que se rechace un DNI con formato inválido. */
  @Test
  void isValid_ShouldReturnFalse_WhenDNIFormatIsInvalid() {
    assertFalse(dniValidator.isValid("INVALIDDNI", null));
  }

  @Test
  void isValid_ShouldReturnFalse_WhenDNIIsEmpty() {
    assertFalse(dniValidator.isValid("1234567A", null));
  }
}

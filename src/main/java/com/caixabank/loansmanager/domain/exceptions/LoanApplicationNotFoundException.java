package com.caixabank.loansmanager.domain.exceptions;

import jakarta.persistence.EntityNotFoundException;

/**
 * Excepción lanzada cuando no se encuentra una solicitud de préstamo en el sistema.
 *
 * <p>Extiende de {@link EntityNotFoundException}.
 */
public class LoanApplicationNotFoundException extends EntityNotFoundException {

  /**
   * Crea una nueva instancia de la excepción con un mensaje detallado.
   *
   * @param message El mensaje que describe la causa del error.
   */
  public LoanApplicationNotFoundException(String message) {
    super(message);
  }
}

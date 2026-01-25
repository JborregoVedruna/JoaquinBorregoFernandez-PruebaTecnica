package com.caixabank.loansmanager.domain.exceptions;

import jakarta.persistence.EntityNotFoundException;

/**
 * Excepción lanzada cuando no se encuentra un usuario en el sistema.
 *
 * <p>Extiende de {@link EntityNotFoundException}.
 */
public class UserNotFoundException extends EntityNotFoundException {

  /**
   * Crea una nueva instancia de la excepción con un mensaje detallado.
   *
   * @param message El mensaje que describe la causa del error.
   */
  public UserNotFoundException(String message) {
    super(message);
  }
}

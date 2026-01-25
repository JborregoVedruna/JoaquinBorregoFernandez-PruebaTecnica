package com.caixabank.loansmanager.domain.model;

import lombok.extern.slf4j.Slf4j;

/**
 * Enumerado que representa los posibles estados de una solicitud de préstamo.
 *
 * <p>{@code @Slf4j}: Habilita el registro de logs.
 */
@Slf4j
public enum LoanStatus {
  /** La solicitud ha sido creada y está esperando revisión. */
  PENDING,
  /** La solicitud ha sido revisada y aprobada. */
  APPROVED,
  /** La solicitud ha sido revisada y rechazada. */
  REJECTED,
  /** La solicitud aprobada ha sido cancelada por el usuario o el sistema. */
  CANCELLED;

  /**
   * Valida si la transición de estado solicitada es permitida por las reglas de negocio.
   *
   * <p>- De PENDING se puede pasar a APPROVED o REJECTED. - De APPROVED se puede pasar a CANCELLED.
   *
   * @param newStatus El nuevo estado al que se desea transicionar.
   * @return true si el cambio es válido, false en caso contrario.
   */
  public boolean isValidChange(LoanStatus newStatus) {
    log.info("Validating status change from {} to {}", this, newStatus);
    // `Pendiente` -> `Aprobada` o `Rechazada`.
    Boolean pedingChange =
        this == LoanStatus.PENDING
            && (newStatus == LoanStatus.APPROVED || newStatus == LoanStatus.REJECTED);
    // `Aprobada` -> `Cancelada`.
    Boolean approvedChange = this == LoanStatus.APPROVED && newStatus == LoanStatus.CANCELLED;
    return pedingChange || approvedChange;
  }
}

package com.caixabank.loansmanager.application.command.updateloanapplicationstatus;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la respuesta tras la actualización del estado de un préstamo.
 *
 * <p>{@code @Data}: Anotación de Lombok para getters y setters. {@code @AllArgsConstructor}: Genera
 * constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class UpdateLoanApplicationStatusResponse {

  /** El modelo de la solicitud de préstamo con el estado ya actualizado. */
  private LoanApplicationModel loanApplication;
}

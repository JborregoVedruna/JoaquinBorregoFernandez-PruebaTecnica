package com.caixabank.loansmanager.application.command.createloanapplication;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la respuesta a una solicitud de creación de préstamo.
 *
 * <p>{@code @Data}: Anotación de Lombok para getters, setters y otros métodos estándar.
 * {@code @AllArgsConstructor}: Genera un constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class CreateLoanApplicationResponse {

  /** El modelo de la solicitud de préstamo que ha sido creada y persistida. */
  private LoanApplicationModel loanApplication;
}

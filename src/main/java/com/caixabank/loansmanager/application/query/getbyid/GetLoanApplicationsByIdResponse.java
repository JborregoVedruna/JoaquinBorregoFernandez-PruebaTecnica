package com.caixabank.loansmanager.application.query.getbyid;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la respuesta con el detalle de una solicitud de préstamo específica.
 *
 * <p>{@code @Data}: Anotación de Lombok para getters y setters. {@code @AllArgsConstructor}: Genera
 * el constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class GetLoanApplicationsByIdResponse {

  /** El modelo de la solicitud de préstamo encontrada. */
  private LoanApplicationModel loanApplication;
}

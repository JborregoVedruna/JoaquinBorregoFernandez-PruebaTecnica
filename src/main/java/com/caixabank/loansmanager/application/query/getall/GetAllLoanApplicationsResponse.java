package com.caixabank.loansmanager.application.query.getall;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la respuesta con el listado paginado de solicitudes de préstamo.
 *
 * <p>{@code @Data}: Anotación de Lombok para métodos de acceso. {@code @AllArgsConstructor}: Genera
 * constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class GetAllLoanApplicationsResponse {

  /** Página que contiene las solicitudes de préstamo encontradas. */
  private PageModel<LoanApplicationModel> loanApplications;

  private PageableModel pageable;
}

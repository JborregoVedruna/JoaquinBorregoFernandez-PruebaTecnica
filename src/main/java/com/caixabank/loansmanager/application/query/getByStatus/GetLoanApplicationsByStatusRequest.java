package com.caixabank.loansmanager.application.query.getByStatus;

import com.caixabank.loansmanager.domain.model.LoanStatus;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.ports.in.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la solicitud para obtener todas las solicitudes de préstamo por su estado.
 *
 * <p>{@code @Data}: Anotación de Lombok para métodos de acceso. {@code @AllArgsConstructor}: Genera
 * constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class GetLoanApplicationsByStatusRequest
    implements Request<GetLoanApplicationsByStatusResponse> {

  /** Estado de las solicitudes a buscar. */
  private LoanStatus loanStatus;

  /** Información de paginación solicitada. */
  private PageableModel pageable;
}

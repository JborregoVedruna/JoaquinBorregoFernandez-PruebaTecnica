package com.caixabank.loansmanager.application.query.getall;

import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.ports.in.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la solicitud para obtener todas las solicitudes de préstamo.
 *
 * <p>{@code @Data}: Anotación de Lombok para métodos de acceso. {@code @AllArgsConstructor}: Genera
 * constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class GetAllLoanApplicationsRequest implements Request<GetAllLoanApplicationsResponse> {

  /** Información de paginación solicitada. */
  private PageableModel pageable;
}

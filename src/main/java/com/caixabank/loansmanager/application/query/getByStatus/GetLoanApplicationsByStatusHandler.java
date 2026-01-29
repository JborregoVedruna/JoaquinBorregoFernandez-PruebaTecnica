package com.caixabank.loansmanager.application.query.getByStatus;

import com.caixabank.loansmanager.domain.ports.in.RequestHandler;
import com.caixabank.loansmanager.domain.ports.out.LoanApplicationJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Manejador encargado de recuperar todas las solicitudes de préstamo por estado de forma paginada.
 *
 * <p>Implementa {@link RequestHandler} para procesar peticiones de tipo {@link
 * GetLoanApplicationsByStatusRequest}.
 *
 * <p>{@code @Slf4j}: Habilita el registro de logs. {@code @Service}: Define esta clase como un
 * servicio de Spring. {@code @AllArgsConstructor}: Genera el constructor para inyección de
 * dependencias.
 */
@Slf4j
@Service
@AllArgsConstructor
public class GetLoanApplicationsByStatusHandler
    implements RequestHandler<
        GetLoanApplicationsByStatusRequest, GetLoanApplicationsByStatusResponse> {

  /** Repositorio de persistencia JPA para las solicitudes de préstamo. */
  private final LoanApplicationJpaRepository jpaRepository;

  /**
   * Procesa la solicitud para obtener todas las solicitudes de préstamo por estado.
   *
   * <p>1. Registra la petición de consulta recibida. 2. Recupera del repositorio la página de
   * solicitudes según los criterios recibidos. 3. Retorna la respuesta con los datos de paginación.
   *
   * @param inputRequest El objeto de solicitud con la información de paginación.
   * @return {@link GetAllLoanApplicationsResponse} con la lista paginada de solicitudes por estado.
   */
  @Override
  public GetLoanApplicationsByStatusResponse handle(GetLoanApplicationsByStatusRequest inputRequest) {
    log.info("Handling GetLoanApplicationsByStatusRequest with inputRequest: {}", inputRequest);
    return new GetLoanApplicationsByStatusResponse(
        jpaRepository.findByStatus(inputRequest.getLoanStatus(), inputRequest.getPageable()), inputRequest.getPageable());
  }

  /**
   * Define el tipo de solicitud que este manejador puede procesar.
   *
   * @return La clase {@link GetAllLoanApplicationsRequest}.
   */
  @Override
  public Class<GetLoanApplicationsByStatusRequest> getRequestType() {
    return GetLoanApplicationsByStatusRequest.class;
  }
}

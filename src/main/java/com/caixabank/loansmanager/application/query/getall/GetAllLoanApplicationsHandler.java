package com.caixabank.loansmanager.application.query.getall;

import com.caixabank.loansmanager.domain.ports.in.RequestHandler;
import com.caixabank.loansmanager.domain.ports.out.LoanApplicationJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Manejador encargado de recuperar todas las solicitudes de préstamo de forma paginada.
 *
 * <p>Implementa {@link RequestHandler} para procesar peticiones de tipo {@link
 * GetAllLoanApplicationsRequest}.
 *
 * <p>{@code @Slf4j}: Habilita el registro de logs. {@code @Service}: Define esta clase como un
 * servicio de Spring. {@code @AllArgsConstructor}: Genera el constructor para inyección de
 * dependencias.
 */
@Slf4j
@Service
@AllArgsConstructor
public class GetAllLoanApplicationsHandler
    implements RequestHandler<GetAllLoanApplicationsRequest, GetAllLoanApplicationsResponse> {

  /** Repositorio de persistencia JPA para las solicitudes de préstamo. */
  private final LoanApplicationJpaRepository jpaRepository;

  /**
   * Procesa la solicitud para obtener todas las solicitudes de préstamo.
   *
   * <p>1. Registra la petición de consulta recibida. 2. Recupera del repositorio la página de
   * solicitudes según los criterios recibidos. 3. Retorna la respuesta con los datos de paginación.
   *
   * @param inputRequest El objeto de solicitud con la información de paginación.
   * @return {@link GetAllLoanApplicationsResponse} con la lista paginada de solicitudes.
   */
  @Override
  public GetAllLoanApplicationsResponse handle(GetAllLoanApplicationsRequest inputRequest) {
    log.info("Handling GetAllLoanApplicationsRequest with inputRequest: {}", inputRequest);
    return new GetAllLoanApplicationsResponse(
        jpaRepository.findAll(inputRequest.getPageable()), inputRequest.getPageable());
  }

  /**
   * Define el tipo de solicitud que este manejador puede procesar.
   *
   * @return La clase {@link GetAllLoanApplicationsRequest}.
   */
  @Override
  public Class<GetAllLoanApplicationsRequest> getRequestType() {
    return GetAllLoanApplicationsRequest.class;
  }
}

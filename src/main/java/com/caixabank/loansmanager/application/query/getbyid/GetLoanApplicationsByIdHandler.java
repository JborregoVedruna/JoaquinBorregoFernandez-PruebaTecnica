package com.caixabank.loansmanager.application.query.getbyid;

import com.caixabank.loansmanager.domain.ports.in.RequestHandler;
import com.caixabank.loansmanager.domain.ports.out.LoanApplicationJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Manejador encargado de recuperar una solicitud de préstamo específica mediante su identificador.
 *
 * <p>Implementa {@link RequestHandler} para procesar peticiones de tipo {@link
 * GetLoanApplicationsByIdRequest}.
 *
 * <p>{@code @Slf4j}: Habilita el registro de logs. {@code @Service}: Define esta clase como un
 * servicio de Spring. {@code @AllArgsConstructor}: Genera el constructor para inyección de
 * dependencias.
 */
@Slf4j
@Service
@AllArgsConstructor
public class GetLoanApplicationsByIdHandler
    implements RequestHandler<GetLoanApplicationsByIdRequest, GetLoanApplicationsByIdResponse> {

  /** Repositorio de persistencia JPA para las solicitudes de préstamo. */
  private final LoanApplicationJpaRepository jpaRepository;

  /**
   * Procesa la solicitud de consulta por ID.
   *
   * <p>1. Registra la petición de consulta recibida con el UUID. 2. Busca en el repositorio la
   * solicitud correspondiente al identificador recibido. 3. Retorna la respuesta con el modelo
   * encontrado.
   *
   * @param inputRequest El objeto de solicitud que contiene el UUID a buscar.
   * @return {@link GetLoanApplicationsByIdResponse} con la solicitud encontrada.
   */
  @Override
  public GetLoanApplicationsByIdResponse handle(GetLoanApplicationsByIdRequest inputRequest) {
    log.info("Handling GetLoanApplicationsByIdRequest with inputRequest: {}", inputRequest);
    return new GetLoanApplicationsByIdResponse(jpaRepository.findById(inputRequest.getUuid()));
  }

  /**
   * Define el tipo de solicitud que este manejador procesa.
   *
   * @return La clase {@link GetLoanApplicationsByIdRequest}.
   */
  @Override
  public Class<GetLoanApplicationsByIdRequest> getRequestType() {
    return GetLoanApplicationsByIdRequest.class;
  }
}

package com.caixabank.loansmanager.application.command.updateloanapplicationstatus;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.ports.in.RequestHandler;
import com.caixabank.loansmanager.domain.ports.out.LoanApplicationJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Manejador encargado de actualizar el estado de una solicitud de préstamo existente.
 *
 * <p>Implementa {@link RequestHandler} para procesar peticiones de tipo {@link
 * UpdateLoanApplicationStatusRequest}.
 *
 * <p>{@code @Slf4j}: Habilita el registro de logs mediante SLF4J. {@code @Service}: Define esta
 * clase como un servicio de Spring. {@code @AllArgsConstructor}: Genera el constructor para
 * inyección de dependencias.
 */
@Slf4j
@Service
@AllArgsConstructor
public class UpdateLoanApplicationStatusHandler
    implements RequestHandler<
        UpdateLoanApplicationStatusRequest, UpdateLoanApplicationStatusResponse> {

  /** Repositorio de persistencia JPA para las solicitudes de préstamo. */
  private final LoanApplicationJpaRepository jpaRepository;

  /**
   * Procesa la actualización del estado de una solicitud de préstamo.
   *
   * <p>1. Registra la solicitud de actualización. 2. Busca la solicitud de préstamo por su
   * identificador UUID. 3. Valida si la transición al nuevo estado es permitida por las reglas de
   * negocio. 4. Actualiza el estado en el modelo si la transición es válida. 5. Persiste el cambio
   * y devuelve la respuesta actualizada.
   *
   * @param inputRequest El objeto de solicitud con el UUID y el nuevo estado.
   * @return {@link UpdateLoanApplicationStatusResponse} con la solicitud ya modificada.
   * @throws IllegalArgumentException Si el cambio de estado solicitado no es válido.
   */
  @Override
  public UpdateLoanApplicationStatusResponse handle(
      UpdateLoanApplicationStatusRequest inputRequest) {
    log.info("Handling UpdateLoanApplicationStatusResponse with inputRequest: {}", inputRequest);
    log.info("Retrieving LoanApplication with id: {}", inputRequest.getUuid());
    LoanApplicationModel loanApplicationModel = jpaRepository.findById(inputRequest.getUuid());
    log.info("Verificando si el cambio de estado es valido");
    if (!loanApplicationModel.getStatus().isValidChange(inputRequest.getLoanStatus())) {
      log.error("Invalid status change");
      throw new IllegalArgumentException("Invalid status change");
    }
    log.info("Actualizando estado de la solicitud");
    loanApplicationModel.setStatus(inputRequest.getLoanStatus());
    log.info("Estado de la solicitud actualizado");
    return new UpdateLoanApplicationStatusResponse(jpaRepository.update(loanApplicationModel));
  }

  /**
   * Devuelve el tipo de solicitud que este manejador puede procesar.
   *
   * @return La clase {@link UpdateLoanApplicationStatusRequest}.
   */
  @Override
  public Class<UpdateLoanApplicationStatusRequest> getRequestType() {
    return UpdateLoanApplicationStatusRequest.class;
  }
}

package com.caixabank.loansmanager.application.command.createloanapplication;

import com.caixabank.loansmanager.domain.model.LoanStatus;
import com.caixabank.loansmanager.domain.ports.in.RequestHandler;
import com.caixabank.loansmanager.domain.ports.out.LoanApplicationJpaRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Manejador encargado de la lógica para la creación de solicitudes de préstamo.
 *
 * <p>Implementa {@link RequestHandler} para procesar peticiones de tipo {@link
 * CreateLoanApplicationRequest}.
 *
 * <p>{@code @Slf4j}: Habilita el registro de logs mediante SLF4J. {@code @Service}: Indica que esta
 * clase es un componente de servicio de Spring. {@code @AllArgsConstructor}: Genera el constructor
 * para la inyección de dependencias.
 */
@Slf4j
@Service
@AllArgsConstructor
public class CreateLoanApplicationHandler
    implements RequestHandler<CreateLoanApplicationRequest, CreateLoanApplicationResponse> {

  /** Repositorio de persistencia JPA para las solicitudes de préstamo. */
  private final LoanApplicationJpaRepository jpaRepository;

  /**
   * Procesa la creación de una nueva solicitud de préstamo.
   *
   * <p>1. Registra la petición recibida. 2. Establece la fecha de creación actual. 3. Inicializa el
   * estado de la solicitud a PENDING. 4. Asocia el usuario a la solicitud. 5. Persiste la solicitud
   * en la base de datos y devuelve la respuesta.
   *
   * @param inputRequest El objeto de solicitud con los datos del préstamo y el usuario.
   * @return {@link CreateLoanApplicationResponse} que contiene la solicitud persistida.
   */
  @Override
  public CreateLoanApplicationResponse handle(CreateLoanApplicationRequest inputRequest) {
    log.info("Handling CreateLoanApplicationRequest with inputRequest: {}", inputRequest);
    inputRequest.getLoanApplication().setCreatedDate(LocalDateTime.now());
    inputRequest.getLoanApplication().setStatus(LoanStatus.PENDING);
    inputRequest.getLoanApplication().setUserModel(inputRequest.getUserModel());
    return new CreateLoanApplicationResponse(jpaRepository.save(inputRequest.getLoanApplication()));
  }

  /**
   * Obtiene el tipo de clase de la solicitud que este manejador puede procesar.
   *
   * @return La clase {@link CreateLoanApplicationRequest}.
   */
  @Override
  public Class<CreateLoanApplicationRequest> getRequestType() {
    return CreateLoanApplicationRequest.class;
  }
}

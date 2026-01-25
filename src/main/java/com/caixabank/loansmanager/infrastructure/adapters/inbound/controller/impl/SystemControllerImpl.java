package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.impl;

import com.caixabank.loansmanager.application.mediator.Mediator;
import com.caixabank.loansmanager.application.query.getall.GetAllLoanApplicationsRequest;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.SystemController;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.converters.InboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación del controlador REST para las operaciones de nivel de sistema.
 *
 * <p>{@code @Slf4j}: Logs. {@code @AllArgsConstructor}: Inyección de dependencias.
 * {@code @CrossOrigin}: CORS. {@code @RestController}: Controlador REST.
 */
@Slf4j
@AllArgsConstructor
@CrossOrigin
@RestController
public class SystemControllerImpl implements SystemController {

  /** Conversor para transformar entre modelos y DTOs. */
  private final InboundConverter inboundConverter;

  /** Mediador para despacho de solicitudes de aplicación. */
  private final Mediator mediator;

  /**
   * {@inheritDoc}
   *
   * <p>Despacha una consulta de listado global al mediador.
   */
  @Override
  public ResponseEntity<Page<LoanApplicationOutput>> getAllLoanApplications(Pageable pageable) {
    log.info("Received get all loan applications request");
    return ResponseEntity.ok()
        .body(
            inboundConverter.toLoanApplicationOutputPage(
                mediator
                    .dispatch(
                        new GetAllLoanApplicationsRequest(
                            inboundConverter.toPageableModel(pageable)))
                    .getLoanApplications()));
  }
}

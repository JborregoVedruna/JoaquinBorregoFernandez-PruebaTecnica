package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.impl;

import com.caixabank.loansmanager.application.command.updateloanapplicationstatus.UpdateLoanApplicationStatusRequest;
import com.caixabank.loansmanager.application.mediator.Mediator;
import com.caixabank.loansmanager.application.query.getbyid.GetLoanApplicationsByIdRequest;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.ManagerController;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.converters.InboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanStatusDto;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación del controlador REST para las operaciones de gestión de préstamos (Manager).
 *
 * <p>{@code @Slf4j}: Logs. {@code @AllArgsConstructor}: Inyección de dependencias.
 * {@code @CrossOrigin}: CORS. {@code @RestController}: Controlador REST.
 */
@Slf4j
@AllArgsConstructor
@CrossOrigin
@RestController
public class ManagerControllerImpl implements ManagerController {

  /** Conversor para transformación de datos. */
  private final InboundConverter inboundConverter;

  /** Mediador para despacho de consultas y comandos. */
  private final Mediator mediator;

  /**
   * {@inheritDoc}
   *
   * <p>Despacha una consulta por ID al mediador y devuelve el resultado convertido.
   */
  @Override
  public ResponseEntity<LoanApplicationOutput> getLoanApplicationById(UUID uuid) {
    log.info("Received get request for loan {}", uuid);
    return ResponseEntity.ok()
        .body(
            inboundConverter.toLoanApplicationOutput(
                mediator.dispatch(new GetLoanApplicationsByIdRequest(uuid)).getLoanApplication()));
  }

  /**
   * {@inheritDoc}
   *
   * <p>Despacha el comando de actualización de estado al mediador.
   */
  @Override
  public ResponseEntity<LoanApplicationOutput> updateLoanApplicationStatus(
      UUID uuid, @Valid LoanStatusDto loanStatusDto) {
    log.info(
        "Received status update request for loan {} to status {}", uuid, loanStatusDto.getStatus());
    return ResponseEntity.ok()
        .body(
            inboundConverter.toLoanApplicationOutput(
                mediator
                    .dispatch(
                        new UpdateLoanApplicationStatusRequest(uuid, loanStatusDto.getStatus()))
                    .getLoanApplication()));
  }
}

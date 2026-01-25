package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.impl;

import com.caixabank.loansmanager.application.command.createloanapplication.CreateLoanApplicationRequest;
import com.caixabank.loansmanager.application.mediator.Mediator;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.CustomerController;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.converters.InboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanApplicationInput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.UserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación del controlador REST para las operaciones de clientes.
 *
 * <p>{@code @Slf4j}: Logs. {@code @AllArgsConstructor}: Constructor para inyectar dependencias.
 * {@code @CrossOrigin}: CORS. {@code @RestController}: Controlador REST.
 */
@Slf4j
@AllArgsConstructor
@CrossOrigin
@RestController
public class CustomerControllerImpl implements CustomerController {

  /** Conversor para transformar entre DTOs y modelos de dominio. */
  private final InboundConverter inboundConverter;

  /** Mediador para el despacho de comandos de aplicación. */
  private final Mediator mediator;

  /**
   * {@inheritDoc}
   *
   * <p>Transforma la entrada a modelos de dominio y despacha el comando de creación al mediador.
   */
  @Override
  public ResponseEntity<LoanApplicationOutput> registerALoanApplication(
      UserDTO userLogueado, LoanApplicationInput loanApplicationInput) {
    log.info("Received create loan application request");

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            inboundConverter.toLoanApplicationOutput(
                mediator
                    .dispatch(
                        new CreateLoanApplicationRequest(
                            inboundConverter.toUserModel(userLogueado),
                            inboundConverter.toLoanApplicationModel(loanApplicationInput)))
                    .getLoanApplication()));
  }
}

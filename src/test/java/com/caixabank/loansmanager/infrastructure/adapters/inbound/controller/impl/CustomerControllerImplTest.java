package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.application.command.createloanapplication.CreateLoanApplicationRequest;
import com.caixabank.loansmanager.application.command.createloanapplication.CreateLoanApplicationResponse;
import com.caixabank.loansmanager.application.mediator.Mediator;
import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.converters.InboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanApplicationInput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Clase de prueba unitaria para {@link CustomerControllerImpl}.
 *
 * <p>Simula el flujo de registro de solicitudes de préstamo por parte de los clientes.
 */
@ExtendWith(MockitoExtension.class)
class CustomerControllerImplTest {

  /** Mock del conversor inbound. */
  @Mock private InboundConverter inboundConverter;

  /** Mock del mediador de la aplicación. */
  @Mock private Mediator mediator;

  /** Instancia del controlador bajo prueba. */
  @InjectMocks private CustomerControllerImpl controller;

  /** Prueba que el registro de una solicitud por un cliente devuelva un estado 201 (CREATED). */
  @Test
  void registerALoanApplication_ShouldReturnCreated() {
    LoanApplicationInput input = new LoanApplicationInput();
    LoanApplicationModel model = new LoanApplicationModel();
    CreateLoanApplicationResponse response = new CreateLoanApplicationResponse(model);
    LoanApplicationOutput output = new LoanApplicationOutput();

    UserDTO userDto = new UserDTO();
    UserModel userModel = new UserModel();

    when(inboundConverter.toUserModel(userDto)).thenReturn(userModel);
    when(inboundConverter.toLoanApplicationModel(input)).thenReturn(model);
    when(mediator.dispatch(any(CreateLoanApplicationRequest.class))).thenReturn(response);
    when(inboundConverter.toLoanApplicationOutput(model)).thenReturn(output);

    ResponseEntity<LoanApplicationOutput> result =
        controller.registerALoanApplication(userDto, input);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals(output, result.getBody());
    verify(mediator).dispatch(any(CreateLoanApplicationRequest.class));
  }
}

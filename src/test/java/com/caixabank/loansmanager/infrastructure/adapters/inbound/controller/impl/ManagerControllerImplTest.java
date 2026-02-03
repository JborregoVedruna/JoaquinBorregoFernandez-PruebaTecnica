package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.application.command.updateloanapplicationstatus.UpdateLoanApplicationStatusRequest;
import com.caixabank.loansmanager.application.command.updateloanapplicationstatus.UpdateLoanApplicationStatusResponse;
import com.caixabank.loansmanager.application.mediator.Mediator;
import com.caixabank.loansmanager.application.query.getByStatus.GetLoanApplicationsByStatusRequest;
import com.caixabank.loansmanager.application.query.getByStatus.GetLoanApplicationsByStatusResponse;
import com.caixabank.loansmanager.application.query.getbyid.GetLoanApplicationsByIdRequest;
import com.caixabank.loansmanager.application.query.getbyid.GetLoanApplicationsByIdResponse;
import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.LoanStatus;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.converters.InboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanStatusDto;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Clase de prueba unitaria para {@link ManagerControllerImpl}.
 *
 * <p>Verifica las operaciones exclusivas para el rol de Gestor (Manager).
 */
@ExtendWith(MockitoExtension.class)
class ManagerControllerImplTest {

  /** Mock del conversor inbound. */
  @Mock private InboundConverter inboundConverter;

  /** Mock del mediador. */
  @Mock private Mediator mediator;

  /** Instancia del controlador bajo prueba. */
  @InjectMocks private ManagerControllerImpl controller;

  /** Prueba que la obtención de una solicitud por ID devuelva un estado 200 (OK). */
  @Test
  void getLoanApplicationById_ShouldReturnOk() {
    UUID uuid = UUID.randomUUID();
    LoanApplicationModel model = new LoanApplicationModel();
    GetLoanApplicationsByIdResponse response = new GetLoanApplicationsByIdResponse(model);
    LoanApplicationOutput output = new LoanApplicationOutput();

    when(mediator.dispatch(any(GetLoanApplicationsByIdRequest.class))).thenReturn(response);
    when(inboundConverter.toLoanApplicationOutput(model)).thenReturn(output);

    ResponseEntity<LoanApplicationOutput> result = controller.getLoanApplicationById(uuid);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(output, result.getBody());
  }

  /** Prueba que la actualización de estado de una solicitud devuelva un estado 200 (OK). */
  @Test
  void updateLoanApplicationStatus_ShouldReturnOk() {
    UUID uuid = UUID.randomUUID();
    LoanStatusDto dto = new LoanStatusDto();
    dto.setStatus(LoanStatus.APPROVED);
    LoanApplicationModel model = new LoanApplicationModel();
    UpdateLoanApplicationStatusResponse response = new UpdateLoanApplicationStatusResponse(model);
    LoanApplicationOutput output = new LoanApplicationOutput();

    when(mediator.dispatch(any(UpdateLoanApplicationStatusRequest.class))).thenReturn(response);
    when(inboundConverter.toLoanApplicationOutput(model)).thenReturn(output);

    ResponseEntity<LoanApplicationOutput> result =
        controller.updateLoanApplicationStatus(uuid, dto);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(output, result.getBody());
  }

  /** Prueba que la obtención de solicitudes por estado devuelva un estado 200 (OK). */
  @Test
  void getLoanApplicationByStatus_ShouldReturnOk() {
    String status = "PENDING";
    Pageable pageable = PageRequest.of(0, 10);
    PageableModel pageableModel = new PageableModel(0, 10, "UNSORTED");
    LoanApplicationModel model = new LoanApplicationModel();
    PageModel<LoanApplicationModel> pageModel =
        new PageModel<>(Collections.singletonList(model), 1, 1, 1, 10, 0);
    GetLoanApplicationsByStatusResponse response =
        new GetLoanApplicationsByStatusResponse(pageModel, pageableModel);
    LoanApplicationOutput output = new LoanApplicationOutput();
    Page<LoanApplicationOutput> outputPage =
        new PageImpl<>(Collections.singletonList(output), pageable, 1);

    when(inboundConverter.toPageableModel(pageable)).thenReturn(pageableModel);
    when(mediator.dispatch(any(GetLoanApplicationsByStatusRequest.class))).thenReturn(response);
    when(inboundConverter.toLoanApplicationOutputPage(pageModel, pageableModel))
        .thenReturn(outputPage);

    ResponseEntity<Page<LoanApplicationOutput>> result =
        controller.getLoanApplicationByStatus(status, pageable);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(outputPage, result.getBody());
  }
}

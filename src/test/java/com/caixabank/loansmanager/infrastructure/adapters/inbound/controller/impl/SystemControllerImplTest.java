package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.application.mediator.Mediator;
import com.caixabank.loansmanager.application.query.getall.GetAllLoanApplicationsRequest;
import com.caixabank.loansmanager.application.query.getall.GetAllLoanApplicationsResponse;
import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.converters.InboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Clase de prueba unitaria para {@link SystemControllerImpl}.
 *
 * <p>Verifica el endpoint de listado global para el rol de Sistema.
 */
@ExtendWith(MockitoExtension.class)
class SystemControllerImplTest {

  /** Mock del conversor inbound. */
  @Mock private InboundConverter inboundConverter;

  /** Mock del mediador. */
  @Mock private Mediator mediator;

  /** Instancia del controlador bajo prueba. */
  @InjectMocks private SystemControllerImpl controller;

  /** Prueba que el listado global de solicitudes devuelva un estado 200 (OK). */
  @Test
  void getAllLoanApplications_ShouldReturnOk() {
    Pageable pageable = PageRequest.of(0, 10);
    com.caixabank.loansmanager.domain.model.PageableModel pageableModel =
        new com.caixabank.loansmanager.domain.model.PageableModel(0, 10, "");
    LoanApplicationModel model = new LoanApplicationModel();
    PageModel<LoanApplicationModel> pageModel =
        new PageModel<>(Collections.singletonList(model), 1L, 1, 1, 10, 0);
    GetAllLoanApplicationsResponse response =
        new GetAllLoanApplicationsResponse(pageModel, pageableModel);
    LoanApplicationOutput output = new LoanApplicationOutput();
    org.springframework.data.domain.Page<LoanApplicationOutput> outputPage =
        new org.springframework.data.domain.PageImpl<>(
            Collections.singletonList(output), pageable, 1);

    when(inboundConverter.toPageableModel(pageable)).thenReturn(pageableModel);
    when(mediator.dispatch(any(GetAllLoanApplicationsRequest.class))).thenReturn(response);
    when(inboundConverter.toLoanApplicationOutputPage(pageModel, pageableModel))
        .thenReturn(outputPage);

    ResponseEntity<Page<LoanApplicationOutput>> result =
        controller.getAllLoanApplications(pageable);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(1, result.getBody().getContent().size());
  }
}

package com.caixabank.loansmanager.application.query.getByStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.LoanStatus;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.ports.out.LoanApplicationJpaRepository;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Clase de prueba unitaria para {@link GetLoanApplicationsByStatusHandler}. */
@ExtendWith(MockitoExtension.class)
class GetLoanApplicationsByStatusHandlerTest {

  @Mock private LoanApplicationJpaRepository jpaRepository;

  @InjectMocks private GetLoanApplicationsByStatusHandler handler;

  @Test
  void handle_ShouldReturnResponseFromRepository() {
    LoanStatus status = LoanStatus.PENDING;
    PageableModel pageableModel = new PageableModel(0, 10, "UNSORTED");
    GetLoanApplicationsByStatusRequest request =
        new GetLoanApplicationsByStatusRequest(status, pageableModel);

    LoanApplicationModel model = new LoanApplicationModel();
    PageModel<LoanApplicationModel> pageModel =
        new PageModel<>(Collections.singletonList(model), 1, 1, 1, 10, 0);

    when(jpaRepository.findByStatus(status, pageableModel)).thenReturn(pageModel);

    GetLoanApplicationsByStatusResponse response = handler.handle(request);

    assertEquals(pageModel, response.getLoanApplications());
    assertEquals(pageableModel, response.getPageable());
  }

  @Test
  void getRequestType_ShouldReturnCorrectType() {
    assertEquals(GetLoanApplicationsByStatusRequest.class, handler.getRequestType());
  }
}

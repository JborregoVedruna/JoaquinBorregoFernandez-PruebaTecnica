package com.caixabank.loansmanager.application.query.getall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.ports.out.LoanApplicationJpaRepository;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Clase de prueba unitaria para {@link GetAllLoanApplicationsHandler}.
 *
 * <p>Verifica la recuperación paginada de todas las solicitudes de préstamo.
 */
@ExtendWith(MockitoExtension.class)
class GetAllLoanApplicationsHandlerTest {

  /** Mock del repositorio de solicitudes. */
  @Mock private LoanApplicationJpaRepository jpaRepository;

  /** Instancia del manejador bajo prueba. */
  @InjectMocks private GetAllLoanApplicationsHandler handler;

  /**
   * Prueba que el manejador devuelva una página de resultados cuando el repositorio responde
   * correctamente.
   */
  @Test
  void handle_ShouldReturnResponse_WhenRepositoryReturnsPage() {
    PageableModel pageableModel = new PageableModel(0, 10, "");
    GetAllLoanApplicationsRequest request = new GetAllLoanApplicationsRequest(pageableModel);
    PageModel<LoanApplicationModel> pageModel =
        new PageModel<>(Collections.emptyList(), 0, 1, 0, 10, 0);

    when(jpaRepository.findAll(pageableModel)).thenReturn(pageModel);

    GetAllLoanApplicationsResponse response = handler.handle(request);

    assertEquals(pageModel, response.getLoanApplications());
    verify(jpaRepository).findAll(pageableModel);
  }
}

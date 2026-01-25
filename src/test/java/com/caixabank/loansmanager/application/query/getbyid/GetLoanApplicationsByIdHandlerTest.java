package com.caixabank.loansmanager.application.query.getbyid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.ports.out.LoanApplicationJpaRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Clase de prueba unitaria para {@link GetLoanApplicationsByIdHandler}.
 *
 * <p>Verifica la búsqueda de una solicitud específica mediante su identificador único.
 */
@ExtendWith(MockitoExtension.class)
class GetLoanApplicationsByIdHandlerTest {

  /** Mock del repositorio de solicitudes. */
  @Mock private LoanApplicationJpaRepository jpaRepository;

  /** Instancia del manejador bajo prueba. */
  @InjectMocks private GetLoanApplicationsByIdHandler handler;

  /**
   * Prueba que el manejador devuelva el modelo de solicitud correcto cuando el repositorio lo
   * encuentra.
   */
  @Test
  void handle_ShouldReturnResponse_WhenRepositoryReturnsModel() {
    UUID uuid = UUID.randomUUID();
    LoanApplicationModel model = new LoanApplicationModel();
    GetLoanApplicationsByIdRequest request = new GetLoanApplicationsByIdRequest(uuid);

    when(jpaRepository.findById(uuid)).thenReturn(model);

    GetLoanApplicationsByIdResponse response = handler.handle(request);

    assertEquals(model, response.getLoanApplication());
    verify(jpaRepository).findById(uuid);
  }
}

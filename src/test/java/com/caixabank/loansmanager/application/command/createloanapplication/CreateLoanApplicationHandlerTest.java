package com.caixabank.loansmanager.application.command.createloanapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.domain.ports.out.LoanApplicationJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Clase de prueba unitaria para {@link CreateLoanApplicationHandler}.
 *
 * <p>Utiliza Mockito para simular las dependencias de persistencia.
 */
@ExtendWith(MockitoExtension.class)
class CreateLoanApplicationHandlerTest {

  /** Mock del repositorio de solicitudes de préstamo. */
  @Mock private LoanApplicationJpaRepository jpaRepository;

  /** Instancia del manejador con los mocks inyectados. */
  @InjectMocks private CreateLoanApplicationHandler handler;

  /**
   * Prueba que el manejador devuelva una respuesta exitosa cuando el repositorio guarda
   * correctamente el modelo.
   */
  @Test
  void handle_ShouldReturnResponse_WhenRepositorySaves() {
    LoanApplicationModel model = new LoanApplicationModel();
    CreateLoanApplicationRequest request = new CreateLoanApplicationRequest(new UserModel(), model);

    when(jpaRepository.save(model)).thenReturn(model);

    CreateLoanApplicationResponse response = handler.handle(request);

    assertEquals(model, response.getLoanApplication());
    verify(jpaRepository).save(model);
  }
}

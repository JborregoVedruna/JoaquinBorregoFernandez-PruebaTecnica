package com.caixabank.loansmanager.application.command.updateloanapplicationstatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.LoanStatus;
import com.caixabank.loansmanager.domain.ports.out.LoanApplicationJpaRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Clase de prueba unitaria para {@link UpdateLoanApplicationStatusHandler}.
 *
 * <p>Prueba las transiciones de estado de las solicitudes de préstamo y las reglas de negocio
 * asociadas.
 */
@ExtendWith(MockitoExtension.class)
class UpdateLoanApplicationStatusHandlerTest {

  /** Mock del repositorio de solicitudes de préstamo. */
  @Mock private LoanApplicationJpaRepository jpaRepository;

  /** Instancia del manejador bajo prueba. */
  @InjectMocks private UpdateLoanApplicationStatusHandler handler;

  /**
   * Prueba que el estado se actualice correctamente cuando la transición de estado solicitada es
   * válida.
   */
  @Test
  void handle_ShouldUpdateStatus_WhenTransitionIsValid() {
    UUID uuid = UUID.randomUUID();
    LoanApplicationModel model = new LoanApplicationModel();
    model.setStatus(LoanStatus.PENDING);

    UpdateLoanApplicationStatusRequest request =
        new UpdateLoanApplicationStatusRequest(uuid, LoanStatus.APPROVED);

    when(jpaRepository.findById(uuid)).thenReturn(model);
    when(jpaRepository.update(model)).thenReturn(model);

    UpdateLoanApplicationStatusResponse response = handler.handle(request);

    assertEquals(LoanStatus.APPROVED, model.getStatus());
    assertEquals(model, response.getLoanApplication());
    verify(jpaRepository).update(model);
  }

  /**
   * Verifica que el manejador lance una excepción cuando se intenta realizar una transición de
   * estado inválida.
   */
  @Test
  void handle_ShouldThrowException_WhenTransitionIsInvalid() {
    UUID uuid = UUID.randomUUID();
    LoanApplicationModel model = new LoanApplicationModel();
    model.setStatus(LoanStatus.PENDING);

    UpdateLoanApplicationStatusRequest request =
        new UpdateLoanApplicationStatusRequest(uuid, LoanStatus.PENDING); // INVALID

    when(jpaRepository.findById(uuid)).thenReturn(model);

    assertThrows(IllegalArgumentException.class, () -> handler.handle(request));
  }
}

package com.caixabank.loansmanager.application.command.updateloanapplicationstatus;

import com.caixabank.loansmanager.domain.model.LoanStatus;
import com.caixabank.loansmanager.domain.ports.in.Request;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la solicitud para actualizar el estado de una solicitud de préstamo.
 *
 * <p>{@code @Data}: Anotación de Lombok para generación automática de métodos.
 * {@code @AllArgsConstructor}: Genera constructor con todos los argumentos.
 */
@Data
@AllArgsConstructor
public class UpdateLoanApplicationStatusRequest
    implements Request<UpdateLoanApplicationStatusResponse> {

  /** Identificador único (UUID) de la solicitud de préstamo a actualizar. */
  private UUID uuid;

  /** El nuevo estado deseado para la solicitud de préstamo. */
  private LoanStatus loanStatus;
}

package com.caixabank.loansmanager.application.query.getbyid;

import com.caixabank.loansmanager.domain.ports.in.Request;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la solicitud para obtener una solicitud de préstamo por su ID.
 *
 * <p>{@code @Data}: Anotación de Lombok para métodos de acceso. {@code @AllArgsConstructor}: Genera
 * el constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class GetLoanApplicationsByIdRequest implements Request<GetLoanApplicationsByIdResponse> {

  /** Identificador único (UUID) de la solicitud de préstamo a consultar. */
  private UUID uuid;
}

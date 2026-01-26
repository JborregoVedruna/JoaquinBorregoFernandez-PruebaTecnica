package com.caixabank.loansmanager.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

/**
 * Modelo de dominio que representa una solicitud de préstamo.
 *
 * <p>{@code @Data}: Anotación de Lombok para generar métodos boilerplate.
 */
@Data
public class LoanApplicationModel implements Serializable {
  private static final long serialVersionUID = 1L;

  /** Identificador único (UUID) de la solicitud de préstamo. */
  private UUID uuid;

  /** Cantidad de dinero solicitada para el préstamo. */
  private BigDecimal requestedAmount;

  /** Moneda en la que se solicita el préstamo (por ejemplo, EUR). */
  private String currency;

  /** Fecha y hora en la que se registró la solicitud. */
  private LocalDateTime createdDate;

  /** Estado actual de la solicitud (PENDING, APPROVED, etc.). */
  private LoanStatus status;

  /** El usuario que ha realizado esta solicitud. */
  private UserModel userModel;
}

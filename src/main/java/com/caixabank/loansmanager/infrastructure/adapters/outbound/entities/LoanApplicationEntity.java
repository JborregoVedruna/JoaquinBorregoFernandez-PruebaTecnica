package com.caixabank.loansmanager.infrastructure.adapters.outbound.entities;

import com.caixabank.loansmanager.domain.model.LoanStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

/**
 * Entidad de persistencia que representa una solicitud de préstamo en la base de datos.
 *
 * <p>Mapea directamente a la tabla 'loan_applications'.
 */
@Data
@Entity
@Table(name = "loan_applications")
public class LoanApplicationEntity {

  /** Identificador único (UUID) generado automáticamente. */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "uuid", nullable = false)
  private UUID uuid;

  /** Importe solicitado. */
  @Column(name = "requested_amount", nullable = false, precision = 13, scale = 2)
  private BigDecimal requestedAmount;

  /** Moneda del importe (ej: EUR). */
  @Column(name = "currency", nullable = false, length = 3)
  private String currency;

  /** Fecha de creación del registro. */
  @Column(name = "created_date", nullable = false)
  private LocalDateTime createdDate;

  /** Estado de la solicitud (mapeado por orden en el enum). */
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "status", nullable = false)
  private LoanStatus status;

  /** Relación con el usuario que realizó la solicitud. */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "users_user_uuid", referencedColumnName = "user_uuid", nullable = false)
  private UserEntity user;
}

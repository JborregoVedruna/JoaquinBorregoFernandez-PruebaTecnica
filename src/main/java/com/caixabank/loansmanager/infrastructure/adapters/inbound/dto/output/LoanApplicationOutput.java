package com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output;

import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.domain.model.LoanStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

/**
 * DTO de salida que contiene la información detallada de una solicitud de préstamo.
 *
 * <p>{@code @Schema}: Metadata de OpenAPI. {@code @Data}: Lombok boilerplate.
 */
@Schema(
    description = "Represents a loan application",
    example = Examples.LOAN_APPLICATION_OUTPUT_SAMPLE)
@Data
public class LoanApplicationOutput {

  /** Identificador único universal de la solicitud. */
  @Schema(description = "The uuid of the loan application", example = Examples.RAW_LOAN_UUID)
  private UUID uuid;

  /** Nombre completo del solicitante. */
  @Schema(description = "The name of the applicant", example = Examples.RAW_APPLICANT_NAME)
  private String applicantName;

  /** Importe del préstamo. */
  @Schema(description = "The amount of the loan application", example = Examples.RAW_AMOUNT)
  private BigDecimal requestedAmount;

  /** Divisa del importe. */
  @Schema(description = "The currency of the loan application", example = Examples.RAW_CURRENCY)
  private String currency;

  /** DNI del solicitante. */
  @Schema(description = "The DNI of the applicant", example = Examples.RAW_APPLICANT_DNI)
  private String applicantDni;

  /** Fecha y hora en la que se creó la solicitud. */
  @Schema(
      description = "The creation date of the loan application",
      example = Examples.RAW_CREATED_DATE)
  private LocalDateTime createdDate;

  /** Estado actual en el ciclo de vida del préstamo. */
  @Schema(
      description = "The status of the loan application",
      example = Examples.RAW_STATUS_PENDING,
      allowableValues = {"PENDING", "APPROVED", "REJECTED", "CANCELLED"})
  private LoanStatus status;
}

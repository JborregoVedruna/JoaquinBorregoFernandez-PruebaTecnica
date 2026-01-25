package com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input;

import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.domain.model.LoanStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO que encapsula el nuevo estado para la actualización de un préstamo.
 *
 * <p>{@code @Data}: Lombok boilerplate. {@code @Schema}: OpenAPI metadata.
 */
@Data
@Schema(
    description = "Represents the status of a loan to be edited",
    example = Examples.LOAN_STATUS_UPDATE_SAMPLE)
public class LoanStatusDto {

  /** El nuevo estado deseado para la solicitud. */
  @Schema(
      description = "The status of the loan",
      example = Examples.RAW_STATUS_APPROVED,
      allowableValues = {"PENDING", "APPROVED", "REJECTED", "CANCELLED"},
      requiredMode = Schema.RequiredMode.REQUIRED)
  private LoanStatus status;
}

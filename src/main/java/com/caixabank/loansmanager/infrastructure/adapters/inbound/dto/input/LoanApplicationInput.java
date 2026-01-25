package com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input;

import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

/**
 * DTO de entrada que contiene los datos mínimos para solicitar un préstamo.
 *
 * <p>{@code @Schema}: OpenAPI metadata. {@code @Data}: Lombok methods.
 */
@Schema(
    description = "Represents a loan application to be inserted or updated",
    example = Examples.LOAN_APPLICATION_INPUT_SAMPLE)
@Data
public class LoanApplicationInput {

  /** Importe monetario solicitado para el préstamo. */
  @Schema(
      description = "The amount of the loan application",
      example = Examples.RAW_AMOUNT,
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "requestedAmount is required and must not be null")
  @Digits(
      integer = 13,
      fraction = 2,
      message = "requestedAmount must have 13 digits and 2 decimal places")
  private BigDecimal requestedAmount;

  /** Divisa en la que se solicita el préstamo (código ISO 4217 de 3 caracteres). */
  @Schema(
      description = "The currency of the loan application",
      example = Examples.RAW_CURRENCY,
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "currency is required and must not be blank")
  @Size(min = 3, max = 3, message = "currency must be 3 characters long")
  private String currency;
}

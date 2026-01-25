package com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input;

import java.math.BigDecimal;

import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.domain.validations.DNI;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "Represents a loan application to be inserted or updated", example = Examples.LOAN_APPLICATION_INPUT_SAMPLE)
@Data
public class LoanApplicationInput {

    @Schema(description = "The name of the applicant", example = Examples.RAW_APPLICANT_NAME, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "applicantName is required and must not be blank")
    @Size(min = 3, max = 45, message = "applicantName must be between 3 and 45 characters long")
    private String applicantName;

    @Schema(description = "The amount of the loan application", example = Examples.RAW_AMOUNT, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "requestedAmount is required and must not be null")
    @Digits(integer = 13, fraction = 2, message = "requestedAmount must have 13 digits and 2 decimal places")
    private BigDecimal requestedAmount;

    @Schema(description = "The currency of the loan application", example = Examples.RAW_CURRENCY, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "currency is required and must not be blank")
    @Size(min = 3, max = 3, message = "currency must be 3 characters long")
    private String currency;

    @Schema(description = "The DNI of the applicant", example = Examples.RAW_APPLICANT_DNI, requiredMode = Schema.RequiredMode.REQUIRED)
    @DNI
    private String applicantDni;
}

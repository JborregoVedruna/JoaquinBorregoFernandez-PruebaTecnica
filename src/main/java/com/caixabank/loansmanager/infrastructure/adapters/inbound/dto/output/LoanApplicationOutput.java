package com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.domain.model.LoanStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Represents a loan application", example = Examples.LOAN_APPLICATION_OUTPUT_SAMPLE)
@Data
public class LoanApplicationOutput {

    @Schema(description = "The uuid of the loan application", example = Examples.RAW_LOAN_UUID)
    private UUID uuid;

    @Schema(description = "The name of the applicant", example = Examples.RAW_APPLICANT_NAME)
    private String applicantName;

    @Schema(description = "The amount of the loan application", example = Examples.RAW_AMOUNT)
    private BigDecimal requestedAmount;

    @Schema(description = "The currency of the loan application", example = Examples.RAW_CURRENCY)
    private String currency;

    @Schema(description = "The DNI of the applicant", example = Examples.RAW_APPLICANT_DNI)
    private String applicantDni;

    @Schema(description = "The creation date of the loan application", example = Examples.RAW_CREATED_DATE)
    private LocalDateTime createdDate;

    @Schema(description = "The status of the loan application", example = Examples.RAW_STATUS_PENDING, allowableValues = {
            "PENDING", "APPROVED", "REJECTED", "CANCELLED" })
    private LoanStatus status;
}

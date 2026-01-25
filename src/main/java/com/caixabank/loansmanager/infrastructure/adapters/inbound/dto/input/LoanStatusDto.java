package com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input;

import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.domain.model.LoanStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Represents the status of a loan to be edited", example = Examples.LOAN_STATUS_UPDATE_SAMPLE)
public class LoanStatusDto {
    @Schema(description = "The status of the loan", example = Examples.RAW_STATUS_APPROVED, allowableValues = {
            "PENDING",
            "APPROVED", "REJECTED", "CANCELLED" }, requiredMode = Schema.RequiredMode.REQUIRED)
    private LoanStatus status;
}

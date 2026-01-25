package com.caixabank.loansmanager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class LoanApplicationModel {
    private UUID uuid;

    private String applicantName;

    private BigDecimal requestedAmount;

    private String currency;

    private String applicantDni;

    private LocalDateTime createdDate;

    private LoanStatus status;
}

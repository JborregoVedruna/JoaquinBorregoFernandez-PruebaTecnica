package com.caixabank.loansmanager.application.command.updateloanapplicationstatus;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateLoanApplicationStatusResponse {
    private LoanApplicationModel loanApplication;
}

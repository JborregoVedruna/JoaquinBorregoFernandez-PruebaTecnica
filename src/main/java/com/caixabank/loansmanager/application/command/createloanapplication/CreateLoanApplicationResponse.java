package com.caixabank.loansmanager.application.command.createloanapplication;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateLoanApplicationResponse {
    private LoanApplicationModel loanApplication;
}

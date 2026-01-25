package com.caixabank.loansmanager.application.command.createloanapplication;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.ports.in.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateLoanApplicationRequest implements Request<CreateLoanApplicationResponse> {
    private LoanApplicationModel loanApplication;
}

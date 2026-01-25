package com.caixabank.loansmanager.application.query.getbyid;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetLoanApplicationsByIdResponse {
    private LoanApplicationModel loanApplication;
}

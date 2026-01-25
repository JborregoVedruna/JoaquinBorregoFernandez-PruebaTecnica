package com.caixabank.loansmanager.application.query.getall;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.PageModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllLoanApplicationsResponse {
    private PageModel<LoanApplicationModel> loanApplications;
}

package com.caixabank.loansmanager.application.query.getall;

import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.ports.in.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllLoanApplicationsRequest implements Request<GetAllLoanApplicationsResponse> {
    private PageableModel pageable;
}

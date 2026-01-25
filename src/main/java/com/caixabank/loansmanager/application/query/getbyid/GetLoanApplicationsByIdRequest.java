package com.caixabank.loansmanager.application.query.getbyid;

import java.util.UUID;

import com.caixabank.loansmanager.domain.ports.in.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetLoanApplicationsByIdRequest implements Request<GetLoanApplicationsByIdResponse> {
    private UUID uuid;
}

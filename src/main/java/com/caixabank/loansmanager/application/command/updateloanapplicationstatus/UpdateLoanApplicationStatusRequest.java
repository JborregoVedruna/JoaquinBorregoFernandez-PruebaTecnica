package com.caixabank.loansmanager.application.command.updateloanapplicationstatus;

import java.util.UUID;

import com.caixabank.loansmanager.domain.model.LoanStatus;
import com.caixabank.loansmanager.domain.ports.in.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateLoanApplicationStatusRequest implements Request<UpdateLoanApplicationStatusResponse> {
    private UUID uuid;
    private LoanStatus loanStatus;
}

package com.caixabank.loansmanager.application.command.createloanapplication;

import org.springframework.stereotype.Service;

import com.caixabank.loansmanager.domain.ports.in.RequestHandler;
import com.caixabank.loansmanager.domain.ports.out.JpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class CreateLoanApplicationHandler
        implements RequestHandler<CreateLoanApplicationRequest, CreateLoanApplicationResponse> {
    JpaRepository jpaRepository;

    @Override
    public CreateLoanApplicationResponse handle(CreateLoanApplicationRequest inputRequest) {
        log.info("Handling CreateLoanApplicationRequest with inputRequest: {}", inputRequest);
        return new CreateLoanApplicationResponse(jpaRepository.save(inputRequest.getLoanApplication()));
    }

    @Override
    public Class<CreateLoanApplicationRequest> getRequestType() {
        return CreateLoanApplicationRequest.class;
    }
}

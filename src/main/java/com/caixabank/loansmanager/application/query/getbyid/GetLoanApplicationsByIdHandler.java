package com.caixabank.loansmanager.application.query.getbyid;

import org.springframework.stereotype.Service;

import com.caixabank.loansmanager.domain.ports.in.RequestHandler;
import com.caixabank.loansmanager.domain.ports.out.JpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class GetLoanApplicationsByIdHandler
        implements RequestHandler<GetLoanApplicationsByIdRequest, GetLoanApplicationsByIdResponse> {

    JpaRepository jpaRepository;

    @Override
    public GetLoanApplicationsByIdResponse handle(GetLoanApplicationsByIdRequest inputRequest) {
        log.info("Handling GetLoanApplicationsByIdRequest with inputRequest: {}", inputRequest);
        return new GetLoanApplicationsByIdResponse(jpaRepository.findById(inputRequest.getUuid()));
    }

    @Override
    public Class<GetLoanApplicationsByIdRequest> getRequestType() {
        return GetLoanApplicationsByIdRequest.class;
    }

}

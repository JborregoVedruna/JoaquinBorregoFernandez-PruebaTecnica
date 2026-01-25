package com.caixabank.loansmanager.application.query.getall;

import org.springframework.stereotype.Service;

import com.caixabank.loansmanager.domain.ports.in.RequestHandler;
import com.caixabank.loansmanager.domain.ports.out.JpaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class GetAllLoanApplicationsHandler
        implements RequestHandler<GetAllLoanApplicationsRequest, GetAllLoanApplicationsResponse> {

    JpaRepository jpaRepository;

    @Override
    public GetAllLoanApplicationsResponse handle(GetAllLoanApplicationsRequest inputRequest) {
        log.info("Handling GetAllLoanApplicationsRequest with inputRequest: {}", inputRequest);
        return new GetAllLoanApplicationsResponse(jpaRepository.findAll(inputRequest.getPageable()));
    }

    @Override
    public Class<GetAllLoanApplicationsRequest> getRequestType() {
        return GetAllLoanApplicationsRequest.class;
    }

}

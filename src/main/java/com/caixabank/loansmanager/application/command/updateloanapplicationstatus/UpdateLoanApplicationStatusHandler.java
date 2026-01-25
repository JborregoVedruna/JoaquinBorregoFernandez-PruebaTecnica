package com.caixabank.loansmanager.application.command.updateloanapplicationstatus;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.ports.in.RequestHandler;
import com.caixabank.loansmanager.domain.ports.out.JpaRepository;

@Slf4j
@Service
@AllArgsConstructor
public class UpdateLoanApplicationStatusHandler
        implements RequestHandler<UpdateLoanApplicationStatusRequest, UpdateLoanApplicationStatusResponse> {

    JpaRepository jpaRepository;

    @Override
    public UpdateLoanApplicationStatusResponse handle(UpdateLoanApplicationStatusRequest inputRequest) {
        log.info("Handling UpdateLoanApplicationStatusResponse with inputRequest: {}", inputRequest);
        log.info("Retrieving LoanApplication with id: {}", inputRequest.getUuid());
        LoanApplicationModel loanApplicationModel = jpaRepository.findById(inputRequest.getUuid());
        log.info("Verificando si el cambio de estado es valido");
        if (!loanApplicationModel.getStatus().isValidChange(inputRequest.getLoanStatus())) {
            log.error("Invalid status change");
            throw new IllegalArgumentException("Invalid status change");
        }
        log.info("Actualizando estado de la solicitud");
        loanApplicationModel.setStatus(inputRequest.getLoanStatus());
        log.info("Estado de la solicitud actualizado");
        return new UpdateLoanApplicationStatusResponse(jpaRepository.update(loanApplicationModel));
    }

    @Override
    public Class<UpdateLoanApplicationStatusRequest> getRequestType() {
        return UpdateLoanApplicationStatusRequest.class;
    }

}

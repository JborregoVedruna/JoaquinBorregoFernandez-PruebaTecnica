package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.impl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.caixabank.loansmanager.application.command.updateloanapplicationstatus.UpdateLoanApplicationStatusRequest;
import com.caixabank.loansmanager.application.mediator.Mediator;
import com.caixabank.loansmanager.application.query.getbyid.GetLoanApplicationsByIdRequest;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.ManagerController;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.converters.InboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanStatusDto;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@CrossOrigin
@RestController
public class ManagerControllerImpl implements ManagerController {

        InboundConverter inboundConverter;
        Mediator mediator;

        @Override
        public ResponseEntity<LoanApplicationOutput> getLoanApplicationById(UUID uuid) {
                log.info("Received get request for loan {}", uuid);
                return ResponseEntity.ok().body(
                                inboundConverter.toLoanApplicationOutput(
                                                mediator.dispatch(new GetLoanApplicationsByIdRequest(uuid))
                                                                .getLoanApplication()));
        }

        @Override
        public ResponseEntity<LoanApplicationOutput> updateLoanApplicationStatus(UUID uuid,
                        @Valid LoanStatusDto loanStatusDto) {
                log.info("Received status update request for loan {} to status {}", uuid, loanStatusDto.getStatus());
                return ResponseEntity.ok().body(
                                inboundConverter.toLoanApplicationOutput(
                                                mediator.dispatch(new UpdateLoanApplicationStatusRequest(uuid,
                                                                loanStatusDto.getStatus()))
                                                                .getLoanApplication()));
        }
}

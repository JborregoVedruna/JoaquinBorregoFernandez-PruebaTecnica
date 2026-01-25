package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.caixabank.loansmanager.application.command.createloanapplication.CreateLoanApplicationRequest;
import com.caixabank.loansmanager.application.command.createloanapplication.CreateLoanApplicationResponse;
import com.caixabank.loansmanager.application.mediator.Mediator;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.CustomerController;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.converters.InboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanApplicationInput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@CrossOrigin
@RestController
public class CustomerControllerImpl implements CustomerController {

        InboundConverter inboundConverter;
        Mediator mediator;

        @Override
        public ResponseEntity<LoanApplicationOutput> registerALoanApplication(
                        LoanApplicationInput loanApplicationInput) {
                log.info("Received create loan application request");
                return ResponseEntity.status(HttpStatus.CREATED).body(
                                inboundConverter.toLoanApplicationOutput(
                                                mediator.dispatch(
                                                                new CreateLoanApplicationRequest(
                                                                                inboundConverter.toLoanApplicationModel(
                                                                                                loanApplicationInput)))
                                                                .getLoanApplication()));
        }

}

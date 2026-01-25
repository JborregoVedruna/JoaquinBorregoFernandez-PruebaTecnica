package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.impl;

import com.caixabank.loansmanager.application.command.createloanapplication.CreateLoanApplicationRequest;
import com.caixabank.loansmanager.application.command.createloanapplication.CreateLoanApplicationResponse;
import com.caixabank.loansmanager.application.mediator.Mediator;
import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.converters.InboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanApplicationInput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerImplTest {

    @Mock
    private InboundConverter inboundConverter;
    @Mock
    private Mediator mediator;

    @InjectMocks
    private CustomerControllerImpl controller;

    @Test
    void registerALoanApplication_ShouldReturnCreated() {
        LoanApplicationInput input = new LoanApplicationInput();
        LoanApplicationModel model = new LoanApplicationModel();
        CreateLoanApplicationResponse response = new CreateLoanApplicationResponse(model);
        LoanApplicationOutput output = new LoanApplicationOutput();

        when(inboundConverter.toLoanApplicationModel(input)).thenReturn(model);
        when(mediator.dispatch(any(CreateLoanApplicationRequest.class))).thenReturn(response);
        when(inboundConverter.toLoanApplicationOutput(model)).thenReturn(output);

        ResponseEntity<LoanApplicationOutput> result = controller.registerALoanApplication(input);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(output, result.getBody());
        verify(mediator).dispatch(any(CreateLoanApplicationRequest.class));
    }
}

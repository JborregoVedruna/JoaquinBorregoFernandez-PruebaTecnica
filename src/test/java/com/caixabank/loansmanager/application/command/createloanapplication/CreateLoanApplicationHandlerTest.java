package com.caixabank.loansmanager.application.command.createloanapplication;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.ports.out.JpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateLoanApplicationHandlerTest {

    @Mock
    private JpaRepository jpaRepository;

    @InjectMocks
    private CreateLoanApplicationHandler handler;

    @Test
    void handle_ShouldReturnResponse_WhenRepositorySaves() {
        LoanApplicationModel model = new LoanApplicationModel();
        CreateLoanApplicationRequest request = new CreateLoanApplicationRequest(model);

        when(jpaRepository.save(model)).thenReturn(model);

        CreateLoanApplicationResponse response = handler.handle(request);

        assertEquals(model, response.getLoanApplication());
        verify(jpaRepository).save(model);
    }
}

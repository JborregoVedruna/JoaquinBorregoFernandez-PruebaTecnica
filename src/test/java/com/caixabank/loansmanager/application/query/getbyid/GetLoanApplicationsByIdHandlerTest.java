package com.caixabank.loansmanager.application.query.getbyid;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.ports.out.JpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLoanApplicationsByIdHandlerTest {

    @Mock
    private JpaRepository jpaRepository;

    @InjectMocks
    private GetLoanApplicationsByIdHandler handler;

    @Test
    void handle_ShouldReturnResponse_WhenRepositoryReturnsModel() {
        UUID uuid = UUID.randomUUID();
        LoanApplicationModel model = new LoanApplicationModel();
        GetLoanApplicationsByIdRequest request = new GetLoanApplicationsByIdRequest(uuid);

        when(jpaRepository.findById(uuid)).thenReturn(model);

        GetLoanApplicationsByIdResponse response = handler.handle(request);

        assertEquals(model, response.getLoanApplication());
        verify(jpaRepository).findById(uuid);
    }
}

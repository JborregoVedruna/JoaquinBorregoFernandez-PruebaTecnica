package com.caixabank.loansmanager.application.command.updateloanapplicationstatus;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.LoanStatus;
import com.caixabank.loansmanager.domain.ports.out.JpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateLoanApplicationStatusHandlerTest {

    @Mock
    private JpaRepository jpaRepository;

    @InjectMocks
    private UpdateLoanApplicationStatusHandler handler;

    @Test
    void handle_ShouldUpdateStatus_WhenTransitionIsValid() {
        UUID uuid = UUID.randomUUID();
        LoanApplicationModel model = new LoanApplicationModel();
        model.setStatus(LoanStatus.PENDING);

        UpdateLoanApplicationStatusRequest request = new UpdateLoanApplicationStatusRequest(uuid, LoanStatus.APPROVED);

        when(jpaRepository.findById(uuid)).thenReturn(model);
        when(jpaRepository.update(model)).thenReturn(model);

        UpdateLoanApplicationStatusResponse response = handler.handle(request);

        assertEquals(LoanStatus.APPROVED, model.getStatus());
        assertEquals(model, response.getLoanApplication());
        verify(jpaRepository).update(model);
    }

    @Test
    void handle_ShouldThrowException_WhenTransitionIsInvalid() {
        UUID uuid = UUID.randomUUID();
        LoanApplicationModel model = new LoanApplicationModel();
        model.setStatus(LoanStatus.PENDING);

        UpdateLoanApplicationStatusRequest request = new UpdateLoanApplicationStatusRequest(uuid, LoanStatus.PENDING); // INVALID

        when(jpaRepository.findById(uuid)).thenReturn(model);

        assertThrows(IllegalArgumentException.class, () -> handler.handle(request));
    }
}

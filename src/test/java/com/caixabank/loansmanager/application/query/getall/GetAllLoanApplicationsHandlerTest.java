package com.caixabank.loansmanager.application.query.getall;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.ports.out.JpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllLoanApplicationsHandlerTest {

    @Mock
    private JpaRepository jpaRepository;

    @InjectMocks
    private GetAllLoanApplicationsHandler handler;

    @Test
    void handle_ShouldReturnResponse_WhenRepositoryReturnsPage() {
        PageableModel pageableModel = new PageableModel(0, 10);
        GetAllLoanApplicationsRequest request = new GetAllLoanApplicationsRequest(pageableModel);
        PageModel<LoanApplicationModel> pageModel = new PageModel<>(Collections.emptyList(), 0, 1, 0, 10, 0);

        when(jpaRepository.findAll(pageableModel)).thenReturn(pageModel);

        GetAllLoanApplicationsResponse response = handler.handle(request);

        assertEquals(pageModel, response.getLoanApplications());
        verify(jpaRepository).findAll(pageableModel);
    }
}

package com.caixabank.loansmanager.infrastructure.adapters.inbound.converters;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.LoanStatus;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanApplicationInput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InboundConverterTest {

    // Concrete implementation to test default methods
    private final InboundConverter converter = new InboundConverter() {
        @Override
        public LoanApplicationOutput toLoanApplicationOutput(LoanApplicationModel loanApplication) {
            return null;
        }

        @Override
        public LoanApplicationModel toLoanApplicationModel(LoanApplicationInput loanApplicationInput) {
            return new LoanApplicationModel();
        }
    };

    @Test
    void initializeDefaultValues_ShouldSetDateAndStatus() {
        LoanApplicationModel model = new LoanApplicationModel();

        converter.initializeDefaultValues(model);

        assertNotNull(model.getCreatedDate(), "CreatedDate should be populated");
        assertEquals(LoanStatus.PENDING, model.getStatus(), "Status should be PENDING");
    }
}

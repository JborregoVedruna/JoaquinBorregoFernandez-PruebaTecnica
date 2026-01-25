package com.caixabank.loansmanager.domain.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoanStatusTest {

    @ParameterizedTest
    @CsvSource({
            "PENDING, APPROVED",
            "PENDING, REJECTED",
            "APPROVED, CANCELLED"
    })
    void isValidChange_ShouldReturnTrue_WhenTransitionIsValid(LoanStatus current, LoanStatus next) {
        assertTrue(current.isValidChange(next));
    }

    @ParameterizedTest
    @CsvSource({
            "PENDING, CANCELLED",
            "PENDING, PENDING",
            "APPROVED, PENDING",
            "APPROVED, REJECTED",
            "REJECTED, PENDING",
            "REJECTED, APPROVED",
            "CANCELLED, PENDING"
    })
    void isValidChange_ShouldReturnFalse_WhenTransitionIsInvalid(LoanStatus current, LoanStatus next) {
        assertFalse(current.isValidChange(next));
    }
}

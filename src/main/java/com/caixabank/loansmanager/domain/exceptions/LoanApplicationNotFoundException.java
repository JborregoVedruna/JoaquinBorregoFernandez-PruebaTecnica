package com.caixabank.loansmanager.domain.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class LoanApplicationNotFoundException extends EntityNotFoundException {

    public LoanApplicationNotFoundException(String message) {
        super(message);
    }

}

package com.caixabank.loansmanager.domain.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum LoanStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED;

    public boolean isValidChange(LoanStatus newStatus) {
        log.info("Validating status change from {} to {}", this, newStatus);
        // `Pendiente` -> `Aprobada` o `Rechazada`.
        Boolean pedingChange = this == LoanStatus.PENDING &&
                (newStatus == LoanStatus.APPROVED ||
                        newStatus == LoanStatus.REJECTED);
        // `Aprobada` -> `Cancelada`.
        Boolean approvedChange = this == LoanStatus.APPROVED &&
                newStatus == LoanStatus.CANCELLED;
        return pedingChange || approvedChange;
    }
}

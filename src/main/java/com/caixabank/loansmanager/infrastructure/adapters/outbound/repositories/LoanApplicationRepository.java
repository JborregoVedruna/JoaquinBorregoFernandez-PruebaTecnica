package com.caixabank.loansmanager.infrastructure.adapters.outbound.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.LoanApplicationEntity;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, UUID> {

}

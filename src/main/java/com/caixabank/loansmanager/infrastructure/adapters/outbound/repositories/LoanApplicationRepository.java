package com.caixabank.loansmanager.infrastructure.adapters.outbound.repositories;

import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.LoanApplicationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repositorio de Spring Data JPA para la entidad {@link LoanApplicationEntity}. */
@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, UUID> {}

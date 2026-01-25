package com.caixabank.loansmanager.infrastructure.adapters.outbound;

import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import com.caixabank.loansmanager.domain.exceptions.LoanApplicationNotFoundException;
import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.ports.out.JpaRepository;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.converters.OutboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.repositories.LoanApplicationRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class JpaRepositoryImpl implements JpaRepository {

    OutboundConverter outboundConverter;
    LoanApplicationRepository loanApplicationRepository;

    @Override
    @Caching(put = @CachePut(value = "loanApplications", key = "#result.uuid"), evict = @CacheEvict(value = "loanApplicationsPage", allEntries = true))
    public LoanApplicationModel save(LoanApplicationModel loanApplication) {
        log.info("Storing loan application: {}", loanApplication);
        return outboundConverter.toModel(loanApplicationRepository.save(outboundConverter.toEntity(loanApplication)));
    }

    @Override
    @Cacheable(value = "loanApplicationsPage", key = "#pageableModel.page + '-' + #pageableModel.size")
    public PageModel<LoanApplicationModel> findAll(PageableModel pageableModel) {
        log.info("Retrieving all loan applications");
        return outboundConverter.toLoanApplicationModelPage(
                loanApplicationRepository.findAll(outboundConverter.toPageable(pageableModel)));
    }

    @Override
    @Cacheable(value = "loanApplications", key = "#uuid")
    public LoanApplicationModel findById(UUID uuid) {
        log.info("Retrieving loan application with id: {}", uuid);
        return outboundConverter.toModel(loanApplicationRepository.findById(uuid).orElseThrow(
                () -> new LoanApplicationNotFoundException("Loan application not found with id: " + uuid)));
    }

    @Override
    @Caching(put = @CachePut(value = "loanApplications", key = "#result.uuid"), evict = @CacheEvict(value = "loanApplicationsPage", allEntries = true))
    public LoanApplicationModel update(LoanApplicationModel loanApplication) {
        log.info("Updating loan application to {}", loanApplication);
        return outboundConverter.toModel(loanApplicationRepository.save(outboundConverter.toEntity(loanApplication)));
    }

}

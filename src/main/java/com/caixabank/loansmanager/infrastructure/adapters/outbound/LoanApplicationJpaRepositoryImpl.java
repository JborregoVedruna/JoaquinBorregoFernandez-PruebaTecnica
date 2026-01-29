package com.caixabank.loansmanager.infrastructure.adapters.outbound;

import com.caixabank.loansmanager.domain.exceptions.LoanApplicationNotFoundException;
import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.LoanStatus;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.ports.out.LoanApplicationJpaRepository;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.converters.OutboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.repositories.LoanApplicationRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

/**
 * Implementación del puerto de salida {@link LoanApplicationJpaRepository} para la persistencia de
 * solicitudes de préstamo utilizando Spring Data JPA.
 *
 * <p>Incluye gestión de caché para mejorar el rendimiento.
 *
 * <p>{@code @Slf4j}: Registro de logs. {@code @Component}: Define esta clase como componente de
 * Spring. {@code @AllArgsConstructor}: Constructor para inyección.
 */
@Slf4j
@Component
@AllArgsConstructor
public class LoanApplicationJpaRepositoryImpl implements LoanApplicationJpaRepository {

  /** Conversor para transformar entre entidades de base de datos y modelos de dominio. */
  private final OutboundConverter outboundConverter;

  /** Repositorio de Spring Data JPA para el acceso directo a la base de datos. */
  private final LoanApplicationRepository loanApplicationRepository;

  /**
   * {@inheritDoc}
   *
   * <p>Guarda la solicitud en la base de datos y actualiza la caché.
   */
  @Override
  @Caching(
      put = @CachePut(value = "loanApplications", key = "#result.uuid"),
      evict = @CacheEvict(value = "loanApplicationsPage", allEntries = true))
  public LoanApplicationModel save(LoanApplicationModel loanApplication) {
    log.info("Storing loan application: {}", loanApplication);
    return outboundConverter.toLoanApplicationModel(
        loanApplicationRepository.save(outboundConverter.toLoanApplicationEntity(loanApplication)));
  }

  /**
   * {@inheritDoc}
   *
   * <p>Recupera una página de solicitudes de préstamo desde la base de datos o la caché.
   */
  @Override
  @Cacheable(
      value = "loanApplicationsPage",
      key = "#pageableModel.page + '-' + #pageableModel.size")
  public PageModel<LoanApplicationModel> findAll(PageableModel pageableModel) {
    log.info("Retrieving all loan applications");
    return outboundConverter.toLoanApplicationModelPage(
        loanApplicationRepository.findAll(outboundConverter.toPageable(pageableModel)));
  }

  /**
   * {@inheritDoc}
   *
   * <p>Busca la solicitud por UUID, lanzando excepción si no existe.
   */
  @Override
  @Cacheable(value = "loanApplications", key = "#uuid")
  public LoanApplicationModel findById(UUID uuid) {
    log.info("Retrieving loan application with id: {}", uuid);
    return outboundConverter.toLoanApplicationModel(
        loanApplicationRepository
            .findById(uuid)
            .orElseThrow(
                () ->
                    new LoanApplicationNotFoundException(
                        "Loan application not found with id: " + uuid)));
  }

  /**
   * {@inheritDoc}
   *
   * <p>Actualiza la solicitud y sincroniza la caché.
   */
  @Override
  @Caching(
      put = @CachePut(value = "loanApplications", key = "#result.uuid"),
      evict = @CacheEvict(value = "loanApplicationsPage", allEntries = true))
  public LoanApplicationModel update(LoanApplicationModel loanApplication) {
    log.info("Updating loan application to {}", loanApplication);
    return outboundConverter.toLoanApplicationModel(
        loanApplicationRepository.save(outboundConverter.toLoanApplicationEntity(loanApplication)));
  }

  /**
   * {@inheritDoc}
   *
   * <p>Busca las solicitudes de préstamo por estado, lanzando excepción si no se encuentran.
   */
  @Override
  public PageModel<LoanApplicationModel> findByStatus(LoanStatus status, PageableModel pageableModel) {
    log.info("Retrieving all loan applications with status {}", status);
    return outboundConverter.toLoanApplicationModelPage(
        loanApplicationRepository.findByStatus(status, outboundConverter.toPageable(pageableModel)));
  }
}

package com.caixabank.loansmanager.domain.ports.out;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.LoanStatus;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import java.util.UUID;

/** Puerto de salida que define las operaciones de persistencia para solicitudes de préstamo. */
public interface LoanApplicationJpaRepository {
  /**
   * Guarda una nueva solicitud de préstamo en el sistema.
   *
   * @param loanApplication El modelo de la solicitud a persistir.
   * @return El modelo persistido con su identificador generado.
   */
  LoanApplicationModel save(LoanApplicationModel loanApplication);

  /**
   * Recupera todas las solicitudes de préstamo de forma paginada.
   *
   * @param pageableModel Los criterios de paginación.
   * @return Una página de modelos de solicitud de préstamo.
   */
  PageModel<LoanApplicationModel> findAll(PageableModel pageableModel);

  /**
   * Busca una solicitud de préstamo por su identificador único.
   *
   * @param uuid El identificador de la solicitud.
   * @return La solicitud encontrada.
   * @throws LoanApplicationNotFoundException Si no existe la solicitud.
   */
  LoanApplicationModel findById(UUID uuid);

  /**
   * Actualiza los datos de una solicitud de préstamo ya existente.
   *
   * @param loanApplication El modelo con los datos actualizados.
   * @return El modelo actualizado y persistido.
   */
  LoanApplicationModel update(LoanApplicationModel loanApplication);

  /**
   * Recupera todas las solicitudes de préstamo por estado de forma paginada.
   *
   * @param status El estado de la solicitud.
   * @param pageableModel Los criterios de paginación.
   * @return Una página de modelos de solicitud de préstamo.
   */
  PageModel<LoanApplicationModel> findByStatus(LoanStatus status, PageableModel pageableModel);
}

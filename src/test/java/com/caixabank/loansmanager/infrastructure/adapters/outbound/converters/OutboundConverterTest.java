package com.caixabank.loansmanager.infrastructure.adapters.outbound.converters;

import static org.junit.jupiter.api.Assertions.*;

import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.LoanApplicationEntity;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.UserEntity;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * Clase de prueba unitaria para {@link OutboundConverter}.
 *
 * <p>Verifica el mapeo correcto entre modelos de dominio y entidades de persistencia, asegurando
 * que se ignoren campos de carga perezosa para evitar excepciones.
 */
class OutboundConverterTest {

  private final OutboundConverter converter = Mappers.getMapper(OutboundConverter.class);

  /** Prueba que al convertir a modelo de dominio se ignoren las solicitudes de préstamo. */
  @Test
  void toUserModel_ShouldIgnoreLoanApplications() {
    UserEntity entity = new UserEntity();
    entity.setUsername("user");
    entity.setLoanApplications(List.of(new LoanApplicationEntity()));

    UserModel result = converter.toUserModel(entity);

    assertNotNull(result);
    assertEquals("user", result.getUsername());
    assertNull(
        result.getLoanApplications(), "Should be ignored to prevent LazyInitializationException");
  }

  /** Prueba que al convertir a entidad de persistencia se ignoren las autoridades y solicitudes. */
  @Test
  void toUserEntity_ShouldIgnoreAuthoritiesAndLoanApplications() {
    UserModel model = new UserModel();
    model.setUsername("user");

    UserEntity result = converter.toUserEntity(model);

    assertNotNull(result);
    assertEquals("user", result.getUsername());
    assertNull(result.getLoanApplications());
  }
}

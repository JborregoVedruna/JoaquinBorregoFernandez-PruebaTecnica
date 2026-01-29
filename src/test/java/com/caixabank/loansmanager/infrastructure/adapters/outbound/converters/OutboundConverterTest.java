package com.caixabank.loansmanager.infrastructure.adapters.outbound.converters;

import static org.junit.jupiter.api.Assertions.*;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.LoanApplicationEntity;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.UserEntity;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

  @Test
  void toPageable_ShouldConvertCorrectly_WithSort() {
    PageableModel pageableModel = new PageableModel(1, 20, "amount: DESC");
    Pageable result = converter.toPageable(pageableModel);

    assertNotNull(result);
    assertEquals(1, result.getPageNumber());
    assertEquals(20, result.getPageSize());
    assertEquals(Sort.by("amount").descending(), result.getSort());
  }

  @Test
  void toPageable_ShouldConvertCorrectly_Unsorted() {
    PageableModel pageableModel = new PageableModel(0, 10, "UNSORTED");
    Pageable result = converter.toPageable(pageableModel);

    assertNotNull(result);
    assertEquals(Sort.unsorted(), result.getSort());
  }

  @Test
  void toLoanApplicationModelPage_ShouldConvertCorrectly() {
    LoanApplicationEntity entity = new LoanApplicationEntity();
    Pageable pageable = PageRequest.of(0, 10);
    Page<LoanApplicationEntity> page =
        new PageImpl<>(Collections.singletonList(entity), pageable, 1);

    PageModel<LoanApplicationModel> result = converter.toLoanApplicationModelPage(page);

    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(1, result.getContent().size());
  }
}

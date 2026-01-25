package com.caixabank.loansmanager.infrastructure.adapters.outbound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.domain.exceptions.LoanApplicationNotFoundException;
import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.converters.OutboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.LoanApplicationEntity;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.repositories.LoanApplicationRepository;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class JpaRepositoryImplTest {

  @Mock private LoanApplicationRepository loanApplicationRepository;
  @Mock private OutboundConverter outboundConverter;

  @InjectMocks private JpaRepositoryImpl jpaRepositoryImpl;

  @Test
  void save_ShouldConvertAndSave() {
    LoanApplicationModel model = new LoanApplicationModel();
    LoanApplicationEntity entity = new LoanApplicationEntity();

    when(outboundConverter.toEntity(model)).thenReturn(entity);
    when(loanApplicationRepository.save(entity)).thenReturn(entity);
    when(outboundConverter.toModel(entity)).thenReturn(model);

    LoanApplicationModel result = jpaRepositoryImpl.save(model);

    assertEquals(model, result);
    verify(outboundConverter).toEntity(model);
    verify(loanApplicationRepository).save(entity);
    verify(outboundConverter).toModel(entity);
  }

  @Test
  void findById_ShouldReturnModel_WhenFound() {
    UUID uuid = UUID.randomUUID();
    LoanApplicationEntity entity = new LoanApplicationEntity();
    LoanApplicationModel model = new LoanApplicationModel();

    when(loanApplicationRepository.findById(uuid)).thenReturn(Optional.of(entity));
    when(outboundConverter.toModel(entity)).thenReturn(model);

    LoanApplicationModel result = jpaRepositoryImpl.findById(uuid);

    assertEquals(model, result);
  }

  @Test
  void findById_ShouldThrowException_WhenNotFound() {
    UUID uuid = UUID.randomUUID();
    when(loanApplicationRepository.findById(uuid)).thenReturn(Optional.empty());

    assertThrows(LoanApplicationNotFoundException.class, () -> jpaRepositoryImpl.findById(uuid));
  }

  @Test
  void update_ShouldSave() {
    LoanApplicationModel model = new LoanApplicationModel();
    LoanApplicationEntity entity = new LoanApplicationEntity();

    when(outboundConverter.toEntity(model)).thenReturn(entity);
    when(loanApplicationRepository.save(entity)).thenReturn(entity);
    when(outboundConverter.toModel(entity)).thenReturn(model);

    LoanApplicationModel result = jpaRepositoryImpl.update(model);

    assertEquals(model, result);
  }

  @Test
  void findAll_ShouldReturnPageOfModels() {
    PageableModel pageableModel = new PageableModel(0, 10);
    org.springframework.data.domain.Pageable springPageable = PageRequest.of(0, 10);
    LoanApplicationEntity entity = new LoanApplicationEntity();
    Page<LoanApplicationEntity> entityPage =
        new PageImpl<>(Collections.singletonList(entity), springPageable, 1);
    LoanApplicationModel model = new LoanApplicationModel();
    PageModel<LoanApplicationModel> expectedPageModel =
        new PageModel<>(Collections.singletonList(model), 1L, 1, 1, 10, 0);

    when(outboundConverter.toPageable(pageableModel)).thenReturn(springPageable);
    when(loanApplicationRepository.findAll(springPageable)).thenReturn(entityPage);
    when(outboundConverter.toLoanApplicationModelPage(entityPage)).thenReturn(expectedPageModel);

    PageModel<LoanApplicationModel> result = jpaRepositoryImpl.findAll(pageableModel);

    assertEquals(1, result.getContent().size());
    assertEquals(model, result.getContent().get(0));
  }
}

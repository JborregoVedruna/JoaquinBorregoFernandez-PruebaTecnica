package com.caixabank.loansmanager.infrastructure.adapters.outbound.converters;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.LoanApplicationEntity;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Interfaz Mapper para la conversión entre modelos de dominio y entidades de persistencia
 * (outbound).
 *
 * <p>{@code @Mapper}: MapStruct mapper.
 */
@Mapper(componentModel = "spring")
public interface OutboundConverter {

  /** Convierte una entidad de base de datos en un modelo de dominio de solicitud de préstamo. */
  @Mapping(target = "userModel", source = "user")
  public LoanApplicationModel toLoanApplicationModel(LoanApplicationEntity entity);

  /** Convierte un modelo de dominio en una entidad de persistencia de solicitud de préstamo. */
  @Mapping(target = "user", source = "userModel")
  public LoanApplicationEntity toLoanApplicationEntity(LoanApplicationModel model);

  /** Convierte una entidad de usuario en un modelo de dominio UserModel. */
  @Mapping(target = "loanApplications", ignore = true)
  public UserModel toUserModel(UserEntity entity);

  /** Convierte el modelo de dominio UserModel en su entidad de persistencia. */
  @Mapping(target = "authorities", ignore = true)
  @Mapping(target = "loanApplications", ignore = true)
  public UserEntity toUserEntity(UserModel model);

  /** Convierte el modelo de paginación del dominio al objeto Pageable de Spring Data. */
  public default Pageable toPageable(PageableModel pageableModel) {
    return PageRequest.of(pageableModel.getPage(), pageableModel.getSize());
  }

  /** Convierte una página de entidades de base de datos en una página de modelos de dominio. */
  public default PageModel<LoanApplicationModel> toLoanApplicationModelPage(
      Page<LoanApplicationEntity> page) {
    return new PageModel<>(
        page.getContent().stream().map(this::toLoanApplicationModel).toList(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.getNumberOfElements(),
        page.getSize(),
        page.getNumber());
  }

  /** Convierte una página de entidades de usuario en una página de modelos de dominio. */
  public default PageModel<UserModel> toUserModelPage(Page<UserEntity> page) {
    return new PageModel<>(
        page.getContent().stream().map(this::toUserModel).toList(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.getNumberOfElements(),
        page.getSize(),
        page.getNumber());
  }
}

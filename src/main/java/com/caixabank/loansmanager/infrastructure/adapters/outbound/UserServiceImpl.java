package com.caixabank.loansmanager.infrastructure.adapters.outbound;

import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.domain.ports.out.UserServiceI;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.converters.OutboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Implementación del servicio de salida para la carga de usuarios integrándose con Spring Security.
 */
@Component
@AllArgsConstructor
public class UserServiceImpl implements UserServiceI {

  /** Servicio nativo de Spring Security para cargar detalles de usuario. */
  private final UserDetailsService userDetailsService;

  /** Conversor para transformar de entidad de seguridad a modelo de dominio. */
  private final OutboundConverter outboundConverter;

  /**
   * {@inheritDoc}
   *
   * <p>Carga el usuario desde el servicio de seguridad y lo convierte al modelo de dominio.
   */
  @Override
  public UserModel loadUserByUsername(String username) {
    return outboundConverter.toUserModel(
        (UserEntity) userDetailsService.loadUserByUsername(username));
  }
}

package com.caixabank.loansmanager.infrastructure.adapters.outbound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.converters.OutboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Clase de prueba unitaria para {@link UserServiceImpl}.
 *
 * <p>Verifica la carga de usuarios integrándose con los servicios de seguridad.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  /** Mock del servicio nativo de Spring Security. */
  @Mock private UserDetailsService userDetailsService;

  /** Mock del conversor outbound. */
  @Mock private OutboundConverter outboundConverter;

  /** Servicio bajo prueba con mocks inyectados. */
  @InjectMocks private UserServiceImpl userService;

  /**
   * Verifica que la carga por nombre de usuario delegue correctamente y convierta la entidad al
   * modelo.
   */
  @Test
  void loadUserByUsername_ShouldCastAndConvert() {
    UserEntity entity = new UserEntity();
    UserModel model = new UserModel();

    when(userDetailsService.loadUserByUsername("user")).thenReturn(entity);
    when(outboundConverter.toUserModel(entity)).thenReturn(model);

    UserModel result = userService.loadUserByUsername("user");

    assertEquals(model, result);
  }
}

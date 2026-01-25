package com.caixabank.loansmanager.application.command.refreshtoken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.caixabank.loansmanager.domain.model.AccessToken;
import com.caixabank.loansmanager.domain.model.Rol;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.domain.ports.out.JWTProvider;
import com.caixabank.loansmanager.domain.ports.out.UserServiceI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Clase de prueba unitaria para {@link RefreshTokenHandler}.
 *
 * <p>Comprueba la renovación de tokens de acceso mediante el uso de refresh tokens válidos.
 */
@ExtendWith(MockitoExtension.class)
class RefreshTokenHandlerTest {

  /** Mock del proveedor JWT. */
  @Mock private JWTProvider jwtProvider;

  /** Mock del servicio de usuarios. */
  @Mock private UserServiceI userService;

  /** Instancia del manejador inyectada con mocks. */
  @InjectMocks private RefreshTokenHandler handler;

  /**
   * Verifica que se generen nuevos tokens de acceso y refresco si el Refresh Token proporcionado es
   * válido.
   */
  @Test
  void handle_ShouldRefreshToken() {
    AccessToken oldToken = new AccessToken();
    oldToken.setRefreshToken("old_refresh");
    RefreshTokenRequest request = new RefreshTokenRequest(oldToken);

    UserModel user = new UserModel();
    user.setUsername("user");
    user.setRol(Rol.ROLE_CUSTOMER);

    when(jwtProvider.getUsernameFromRefreshToken("old_refresh")).thenReturn("user");
    when(userService.loadUserByUsername("user")).thenReturn(user);
    when(jwtProvider.isRefreshTokenValid("old_refresh", user)).thenReturn(true);
    when(jwtProvider.generateAccessToken(user)).thenReturn("new_access");
    when(jwtProvider.getAccessTokenExpiresIn()).thenReturn(3600L);
    when(jwtProvider.generateRefreshToken(user)).thenReturn("new_refresh");

    RefreshTokenResponse response = handler.handle(request);

    assertEquals("new_access", response.getAccessToken().getAccessToken());
    assertEquals("new_refresh", response.getAccessToken().getRefreshToken());
  }
}

package com.caixabank.loansmanager.application.command.loginuser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.caixabank.loansmanager.domain.model.Rol;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.domain.ports.out.AuthenticationManagerI;
import com.caixabank.loansmanager.domain.ports.out.JWTProvider;
import com.caixabank.loansmanager.domain.ports.out.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Clase de prueba unitaria para {@link LoginUserHandler}.
 *
 * <p>Evalúa la lógica de autenticación y generación de tokens de acceso.
 */
@ExtendWith(MockitoExtension.class)
class LoginUserHandlerTest {

  /** Mock del repositorio de usuarios. */
  @Mock private UserJpaRepository userJpaRepository;

  /** Mock del gestor de autenticación. */
  @Mock private AuthenticationManagerI authenticationManager;

  /** Mock del proveedor de tokens JWT. */
  @Mock private JWTProvider jwtProvider;

  /** Manejador bajo prueba. */
  @InjectMocks private LoginUserHandler handler;

  /**
   * Verifica que el flujo de login autentique las credenciales y devuelva los tokens
   * correspondientes.
   */
  @Test
  void handle_ShouldAuthenticateAndReturnTokens() {
    UserModel user = new UserModel();
    user.setUsername("user");
    user.setRol(Rol.ROLE_CUSTOMER);

    LoginUserRequest request = new LoginUserRequest(user);
    request.getUser().setPassword("pass");

    when(userJpaRepository.findByUsername("user")).thenReturn(user);
    when(jwtProvider.generateAccessToken(user)).thenReturn("access");
    when(jwtProvider.generateRefreshToken(user)).thenReturn("refresh");
    when(jwtProvider.getAccessTokenExpiresIn()).thenReturn(3600L);

    LoginUserResponse response = handler.handle(request);

    assertNotNull(response.getAccessToken());
    assertEquals("access", response.getAccessToken().getAccessToken());
    assertEquals(
        "read write", response.getAccessToken().getScope()); // Actual scope for ROLE_CUSTOMER

    verify(authenticationManager).authenticate("user", "pass");
  }
}

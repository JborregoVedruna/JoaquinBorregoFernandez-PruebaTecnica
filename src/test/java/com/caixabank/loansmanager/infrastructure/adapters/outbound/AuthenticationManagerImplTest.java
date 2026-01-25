package com.caixabank.loansmanager.infrastructure.adapters.outbound;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Clase de prueba unitaria para {@link AuthenticationManagerImpl}.
 *
 * <p>Verifica la delegación de la autenticación al gestor nativo de Spring Security.
 */
@ExtendWith(MockitoExtension.class)
class AuthenticationManagerImplTest {

  /** Mock del AuthenticationManager de Spring Security. */
  @Mock private AuthenticationManager authenticationManager;

  /** Instancia implementada bajo prueba. */
  @InjectMocks private AuthenticationManagerImpl authenticationManagerImpl;

  /**
   * Verifica que el método delegate la llamada al gestor de autenticación con los tokens adecuados.
   */
  @Test
  void authenticate_ShouldDelegateToAuthenticationManager() {
    String username = "user";
    String password = "password";

    authenticationManagerImpl.authenticate(username, password);

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
  }
}

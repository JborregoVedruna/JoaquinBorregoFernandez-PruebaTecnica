package com.caixabank.loansmanager.infrastructure.adapters.outbound;

import com.caixabank.loansmanager.domain.ports.out.AuthenticationManagerI;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * Implementación del puerto {@link AuthenticationManagerI} utilizando el {@link
 * AuthenticationManager} de Spring Security.
 */
@Component
@AllArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManagerI {

  /** Gestor de autenticación nativo de Spring Security. */
  private final AuthenticationManager authenticationManager;

  /**
   * {@inheritDoc}
   *
   * <p>Realiza la autenticación del usuario utilizando el gestor de Spring Security.
   */
  @Override
  public void authenticate(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
  }
}

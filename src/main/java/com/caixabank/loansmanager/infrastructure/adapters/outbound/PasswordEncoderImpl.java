package com.caixabank.loansmanager.infrastructure.adapters.outbound;

import com.caixabank.loansmanager.domain.ports.out.PasswordEncoderI;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Implementación del puerto de salida {@link PasswordEncoderI} para el cifrado de contraseñas. */
@Component
@AllArgsConstructor
public class PasswordEncoderImpl implements PasswordEncoderI {

  /** Codificador de contraseñas configurado en el sistema. */
  private final PasswordEncoder passwordEncoder;

  /**
   * {@inheritDoc}
   *
   * <p>Codifica la contraseña utilizando el algoritmo configurado.
   */
  @Override
  public String encode(CharSequence rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }
}

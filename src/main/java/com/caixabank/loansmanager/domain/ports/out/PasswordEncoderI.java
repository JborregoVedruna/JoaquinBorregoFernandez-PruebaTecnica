package com.caixabank.loansmanager.domain.ports.out;

/** Puerto de salida para la codificación de contraseñas. */
public interface PasswordEncoderI {

  /**
   * Codifica una contraseña en texto plano.
   *
   * @param rawPassword La contraseña original.
   * @return La contraseña codificada de forma segura.
   */
  String encode(CharSequence rawPassword);
}

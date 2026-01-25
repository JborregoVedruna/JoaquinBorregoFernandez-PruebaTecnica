package com.caixabank.loansmanager.infrastructure.adapters.outbound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de prueba unitaria para {@link PasswordEncoderImpl}.
 *
 * <p>Verifica el cifrado de contraseñas delegando en el codificador configurado.
 */
@ExtendWith(MockitoExtension.class)
class PasswordEncoderImplTest {

  /** Mock del codificador de contraseñas de Spring Security. */
  @Mock private PasswordEncoder passwordEncoder;

  /** Instancia implementada bajo prueba. */
  @InjectMocks private PasswordEncoderImpl passwordEncoderImpl;

  /** Verifica que el método delegate correctamente el cifrado al componente interno. */
  @Test
  void encode_ShouldDelegateToPasswordEncoder() {
    String rawPassword = "password";
    String encodedPassword = "encodedPassword";

    when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

    String result = passwordEncoderImpl.encode(rawPassword);

    assertEquals(encodedPassword, result);
    verify(passwordEncoder).encode(rawPassword);
  }
}

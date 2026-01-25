package com.caixabank.loansmanager.application.command.registeruser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.caixabank.loansmanager.domain.model.Rol;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.domain.ports.out.PasswordEncoderI;
import com.caixabank.loansmanager.domain.ports.out.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Clase de prueba unitaria para {@link RegisterUserHandler}.
 *
 * <p>Valida el proceso de registro de nuevos usuarios y el cifrado de contraseñas.
 */
@ExtendWith(MockitoExtension.class)
class RegisterUserHandlerTest {

  /** Mock del repositorio de usuarios. */
  @Mock private UserJpaRepository userJpaRepository;

  /** Mock del codificador de contraseñas. */
  @Mock private PasswordEncoderI passwordEncoder;

  /** Instancia del manejador inyectada con mocks. */
  @InjectMocks private RegisterUserHandler handler;

  /** Verifica que el registro codifique la contraseña y guarde al usuario con el rol adecuado. */
  @Test
  void handle_ShouldRegisterAndSaveUser() {
    UserModel inputUser = new UserModel();
    inputUser.setUsername("testuser");
    inputUser.setPassword("rawPassword");
    RegisterUserRequest request = new RegisterUserRequest(inputUser);

    when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
    when(userJpaRepository.save(any(UserModel.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    RegisterUserResponse response = handler.handle(request);

    assertEquals("encodedPassword", response.getUser().getPassword());
    assertEquals(Rol.ROLE_CUSTOMER, response.getUser().getRol());
    verify(userJpaRepository).save(any(UserModel.class));
  }
}

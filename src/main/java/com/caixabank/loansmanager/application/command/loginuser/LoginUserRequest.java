package com.caixabank.loansmanager.application.command.loginuser;

import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.domain.ports.in.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la solicitud de inicio de sesión de un usuario.
 *
 * <p>{@code @Data}: Anotación de Lombok para generar métodos boilerplate.
 * {@code @AllArgsConstructor}: Genera un constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class LoginUserRequest implements Request<LoginUserResponse> {

  /** Modelo que contiene las credenciales del usuario (nombre de usuario y contraseña). */
  private UserModel user;
}

package com.caixabank.loansmanager.application.command.registeruser;

import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.domain.ports.in.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la solicitud para registrar un nuevo usuario en el sistema.
 *
 * <p>{@code @Data}: Anotación de Lombok para generar métodos boilerplate.
 * {@code @AllArgsConstructor}: Genera un constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class RegisterUserRequest implements Request<RegisterUserResponse> {

  /** Modelo que contiene la información del usuario a registrar. */
  private UserModel user;
}

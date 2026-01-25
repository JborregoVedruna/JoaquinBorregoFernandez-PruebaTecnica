package com.caixabank.loansmanager.domain.ports.out;

import com.caixabank.loansmanager.domain.model.UserModel;

/** Interfaz para el servicio de gestión de usuarios en el dominio. */
public interface UserServiceI {

  /**
   * Carga un usuario a partir de su nombre de usuario.
   *
   * @param username El nombre de usuario a buscar.
   * @return El modelo del usuario encontrado.
   */
  UserModel loadUserByUsername(String username);
}

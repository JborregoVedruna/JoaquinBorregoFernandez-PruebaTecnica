package com.caixabank.loansmanager.domain.ports.out;

import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.model.UserModel;
import java.util.UUID;

/**
 * Puerto de salida para la persistencia de usuarios.
 *
 * <p>Define las operaciones necesarias para gestionar el ciclo de vida de los usuarios en el
 * sistema de almacenamiento.
 */
public interface UserJpaRepository {

  /**
   * Guarda o actualiza un usuario en la base de datos.
   *
   * @param user Modelo de usuario a persistir.
   * @return El modelo del usuario persistido.
   */
  UserModel save(UserModel user);

  /**
   * Recupera todos los usuarios de forma paginada.
   *
   * @param pageableModel Información de paginación.
   * @return Una página de modelos de usuario.
   */
  PageModel<UserModel> findAll(PageableModel pageableModel);

  /**
   * Busca un usuario por su identificador único.
   *
   * @param uuid El UUID del usuario.
   * @return El modelo del usuario encontrado o null si no existe.
   */
  UserModel findById(UUID uuid);

  /**
   * Busca un usuario por su nombre de usuario.
   *
   * @param username El nombre de usuario (login).
   * @return El modelo del usuario encontrado o null si no existe.
   */
  UserModel findByUsername(String username);
}
